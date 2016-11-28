package edu.calpoly.womangr.mangr.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mangrManager";

    // TODO: strip some columns out as not needed
    // Mangas table
    private static final String TABLE_MANGAS = "manga";
    private static final String KEY_NAME = "name";
    private static final String KEY_HREF = "href";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_STATUS = "status";
    private static final String KEY_YEAR = "yearOfRelease";
    private static final String KEY_GENRES = "genres";
    private static final String KEY_INFO = "info";
    private static final String KEY_COVER = "cover";
    private static final String KEY_LAST_UPDATE = "lastUpdate";
    private static final String KEY_CHAPTERS = "chapters";

    // Likes table
    private static final String TABLE_LIKES = "likes";
    private static final String KEY_MANGA_ID = "mangaId";

    // Dislikes table. Reuses KEY_MANGA_ID
    private static final String TABLE_DISLIKES = "dislikes";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MANGAS_TABLE = "CREATE TABLE " + TABLE_MANGAS + "("
                + KEY_NAME + " TEXT,"
                + KEY_HREF + " TEXT PRIMARY KEY,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_ARTIST + " TEXT," + KEY_STATUS + " TEXT,"
                + KEY_YEAR + " TEXT," + KEY_GENRES + " TEXT,"
                + KEY_INFO + " TEXT," + KEY_COVER + " TEXT,"
                + KEY_LAST_UPDATE + " TEXT," + KEY_CHAPTERS + " TEXT)";
        String CREATE_LIKES_TABLE = "CREATE TABLE " + TABLE_LIKES + "("
                + KEY_MANGA_ID + " TEXT PRIMARY KEY)";
        String CREATE_DISLIKES_TABLE = "CREATE TABLE " + TABLE_DISLIKES + "("
                + KEY_MANGA_ID + " TEXT PRIMARY KEY)";

        db.execSQL(CREATE_MANGAS_TABLE);
        db.execSQL(CREATE_LIKES_TABLE);
        db.execSQL(CREATE_DISLIKES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANGAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISLIKES);

        // Create tables again
        onCreate(db);
    }

    public void addManga(SqlMangaModel sqlMangaModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sqlMangaModel.getName());
        values.put(KEY_HREF, sqlMangaModel.getHref());
        values.put(KEY_AUTHOR, sqlMangaModel.getAuthor());
        values.put(KEY_ARTIST , sqlMangaModel.getArtist());
        values.put(KEY_STATUS , sqlMangaModel.getStatus());
        values.put(KEY_GENRES , sqlMangaModel.getGenres());
        values.put(KEY_INFO , sqlMangaModel.getInfo());
        values.put(KEY_COVER , sqlMangaModel.getCover());

        // Inserting Row
        db.insert(TABLE_MANGAS, null, values);
        db.close(); // Closing database connection
    }

    public void addLike(String mangaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MANGA_ID, mangaId);
        db.insert(TABLE_LIKES, null, values);
        db.close();
    }

    public void addDisike(String mangaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MANGA_ID, mangaId);
        db.insert(TABLE_DISLIKES, null, values);
        db.close();
    }

    public SqlMangaModel getManga(String mangaId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MANGAS,
                new String[] { KEY_NAME, KEY_HREF, KEY_AUTHOR, KEY_ARTIST, KEY_STATUS,
                        KEY_GENRES, KEY_INFO, KEY_COVER },
                KEY_HREF + "=?",
                new String[] { mangaId }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SqlMangaModel sqlMangaModel = new SqlMangaModel(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7));
        return sqlMangaModel;
    }

    public boolean hasManga(String mangaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_MANGAS + " WHERE " + KEY_HREF + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(queryString, new String[] {mangaId});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }

        cursor.close();
        db.close();
        return hasObject;
    }

    public boolean hasLike(String mangaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_LIKES + " WHERE " + KEY_MANGA_ID + " =?";
        Cursor cursor = db.rawQuery(queryString, new String[] {mangaId});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
        }

        cursor.close();
        db.close();
        return hasObject;
    }

    public boolean hasDisLike(String mangaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_DISLIKES + " WHERE " + KEY_MANGA_ID + " =?";
        Cursor cursor = db.rawQuery(queryString, new String[] {mangaId});

        boolean hasObject = false;
        if (cursor.moveToFirst()){
            hasObject = true;
        }

        cursor.close();
        db.close();
        return hasObject;
    }

    public List<SqlMangaModel> getAllLikes() {
        ArrayList<SqlMangaModel> likes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LIKES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                likes.add(getManga(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        return likes;
    }

    public List<SqlMangaModel> getAllDislikes() {
        ArrayList<SqlMangaModel> dislikes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_DISLIKES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                dislikes.add(getManga(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        return dislikes;
    }
}
