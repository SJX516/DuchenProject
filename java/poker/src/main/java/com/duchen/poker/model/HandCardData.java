package com.duchen.poker.model;

import java.util.ArrayList;
import java.util.List;

public class HandCardData {

    enum Role {
        BOSS, FARMER_NEXT, FARMER_PRE
    }

    List<Integer> mHandCardList = new ArrayList<>();
    int mHandCardCount = 0;
    Role mRole;
    HandCardValue mHandCardValue;
    List<Integer> mPutCardList = new ArrayList<>();
    CardGroup mPutCardGroup;

    boolean putCards() {
        for (int i = 0; i < mPutCardList.size(); i++) {
            if (!putOneCard(mPutCardList.get(i))) {
                return false;
            }
        }
        mHandCardCount = mHandCardList.size();
        return true;
    }

    boolean putOneCard(int value) {
        if (!mHandCardList.contains(value)) {
            return false;
        } else {
            mHandCardList.remove(mHandCardList.indexOf(value));
            return true;
        }
    }
}
