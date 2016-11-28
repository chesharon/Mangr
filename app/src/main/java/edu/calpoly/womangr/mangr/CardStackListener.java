package edu.calpoly.womangr.mangr;

import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

import com.wenchao.cardstack.CardStack;
import com.wenchao.cardstack.CardStack.CardEventListener;

public class CardStackListener implements CardEventListener {

    @Override
    public boolean swipeEnd(int direction, float distance) {
        //if "return true" the dismiss animation will be triggered
        //if false, the card will move back to stack
        //distance is finger swipe distance in dp

        //the direction indicate swipe direction
        //there are four directions
        //  0  |  1
        // ----------
        //  2  |  3
        if (direction == 1 || direction == 3) {
            Log.d("LIKED", "this manga");
        }
        if (direction == 0 || direction == 2) {
            Log.d("DISLIKED", "this manga");
        }

        return (distance > 300) ? true : false;
    }

    @Override
    public boolean swipeStart(int direction, float distance) {

        return false;
    }

    @Override
    public boolean swipeContinue(int direction, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void discarded(int id, int direction) {
        //this callback invoked when dismiss animation is finished.
    }

    @Override
    public void topCardTapped() {
        //this callback invoked when a top card is tapped by user.
    }
}
