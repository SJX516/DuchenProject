package com.duchen.poker.model;

import com.duchen.poker.model.type.CardGroupType;

import java.util.ArrayList;
import java.util.List;

public class CardGroup {

    CardGroupType mCardGroupType = CardGroupType.ERROR;
    List<Integer> mCardList = new ArrayList<>();
    //该牌型中用于决定大小的值（比如33344中的3）
    int mMaxCard = -1;
    int mValue = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mCardGroupType).append(" ");
        for (int card : mCardList) {
            sb.append(card).append(" ");
        }
        sb.append("  maxCard: ").append(mMaxCard).append("  value: ").append(mValue);
        return sb.toString();
    }

    public List<Integer> getCardList() {
        return mCardList;
    }

    public int getMaxCard() {
        return mMaxCard;
    }

    public void setCardGroupType(CardGroupType cardGroupType) {
        mCardGroupType = cardGroupType;
    }

    public void setCardList(List<Integer> cardList) {
        mCardList = cardList;
    }

    public void setMaxCard(int maxCard) {
        mMaxCard = maxCard;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public CardGroupType getCardGroupType() {
        return mCardGroupType;
    }

}
