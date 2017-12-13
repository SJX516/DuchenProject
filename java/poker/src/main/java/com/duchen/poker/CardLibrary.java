package com.duchen.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardLibrary {

    int[] mCards;
    Random mRandom = new Random();

    public CardLibrary() {
        mCards = new int[18];
        for (int i = 3; i < 16; i++) {
            mCards[i] = 4;
        }
        mCards[16] = 1;
        mCards[17] = 1;
    }

    public List<Integer> getRoundOneCard() {
        List<Integer> cards = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            cards.add(getOneCard());
        }
        return cards;
    }

    public List<Integer> getRoundTwoCard() {
        List<Integer> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(getOneCard());
        }
        return cards;
    }

    public int getOneCard() {
        int start = random();
        if (start < 0 || start > mCards.length) {
            start = 3;
        }
        for (int i = 0; i < mCards.length; i++) {
            int actualIndex = 0;
            actualIndex = i + start;
            if (actualIndex >= mCards.length) {
                actualIndex = actualIndex % mCards.length;
            }

            if (mCards[actualIndex] > 0) {
                mCards[actualIndex]--;
                return actualIndex;
            }
        }
        return -1;
    }

    public static String getCardChar(int i) {
        if (i >= 3 && i <= 10) {
            return i + "";
        } else {
            switch (i) {
                case 11:
                    return "J";
                case 12:
                    return "Q";
                case 13:
                    return "K";
                case 14:
                    return "A";
                case 15:
                    return "2";
                case 16:
                    return "小王";
                case 17:
                    return "大王";
            }
        }
        return "ERROR" + i;
    }

    int random() {
        return mRandom.nextInt(18);
    }

}
