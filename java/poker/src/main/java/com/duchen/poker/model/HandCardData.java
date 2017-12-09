package com.duchen.poker.model;

import java.util.ArrayList;
import java.util.List;

public class HandCardData {

    enum Role {
        BOSS, FARMER_NEXT, FARMER_PRE
    }

    List<Integer> mHandCardList = new ArrayList<>();
    Role mRole;
    HandCardValue mHandCardValue;
    CardGroup mPutCardGroup;

    public boolean noMoreCard() {
        return mHandCardList.isEmpty();
    }

    public void setPutCardGroup(CardGroup putCardGroup) {
        mPutCardGroup = putCardGroup;
    }

    public List<Integer> getHandCardList() {
        return mHandCardList;
    }

    public void putCards() {
        putCards(mPutCardGroup);
    }

    public void putCards(CardGroup cardGroup) {
        List<Integer> putCardList = cardGroup.getCardList();
        mHandCardList.removeAll(putCardList);
    }

    public void addCards(CardGroup cardGroup) {
        List<Integer> cardList = cardGroup.getCardList();
        mHandCardList.addAll(cardList);
    }
}
