package com.duchen.poker.model;

import java.util.List;

public class Player {

    public enum Role {
        BOSS, FARMER_NEXT, FARMER_PRE
    }

    Role mRole;
    HandCardData mHandCardData;

    public Player() {
        mHandCardData = new HandCardData();
    }

    public void setRole(Role role) {
        mRole = role;
    }

    public Role getRole() {
        return mRole;
    }

    public boolean askToBeBoss() {
        return true;
    }

    public void setCards(List<Integer> cards) {
        mHandCardData.setHandCardList(cards);
    }

    public void becomeBoss(List<Integer> threeCards) {
        mHandCardData.addCards(threeCards);
    }

    @Override
    public String toString() {
        return "Player{" + "mRole=" + mRole + ", mHandCardData=" + mHandCardData + '}';
    }
}
