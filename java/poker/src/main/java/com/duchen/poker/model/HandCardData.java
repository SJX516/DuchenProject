package com.duchen.poker.model;

import com.duchen.poker.CardLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandCardData {

    List<Integer> mHandCardList = new ArrayList<>();
    HandCardValue mHandCardValue;
    CardGroup mPutCardGroup;

    public boolean noMoreCard() {
        return mHandCardList.isEmpty();
    }

    public List<Integer> getHandCardList() {
        return mHandCardList;
    }

    public void setHandCardList(List<Integer> handCardList) {
        mHandCardList = handCardList;
    }

    public void addCards(List<Integer> cards) {
        mHandCardList.addAll(cards);
    }

    public void setPutCardGroup(CardGroup putCardGroup) {
        mPutCardGroup = putCardGroup;
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

    @Override
    public String toString() {
        int[] arrays = new int[mHandCardList.size()];
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = mHandCardList.get(i);
        }
        Arrays.sort(arrays);
        StringBuilder sb = new StringBuilder();
        sb.append("HandCardData{" + "mHandCardList=[");
        for (Integer array : arrays) {
            sb.append(CardLibrary.getCardChar(array));
        }
        sb.append("]");
        sb.append(", mHandCardValue=" + mHandCardValue + ", " + "mPutCardGroup=" + mPutCardGroup + '}');
        return sb.toString();
    }
}
