package com.duchen.poker;

import com.duchen.poker.model.Player;

import java.util.Random;

public class Poker {

    public static int PLAYER_NUM = 3;

    public static void main(String[] args) {
        Poker poker = new Poker();
        poker.startNewGame();
    }

    Player[] mPlayers = new Player[PLAYER_NUM];
    CardLibrary mCardLibrary;
    int mStartIndex = 0;

    private void startNewGame() {
        mCardLibrary = new CardLibrary();
        mStartIndex = new Random().nextInt(PLAYER_NUM);

        for (int i = 0; i < PLAYER_NUM; i++) {
            mPlayers[getActualIndex(i + mStartIndex)] = new Player();
            mPlayers[getActualIndex(i + mStartIndex)].setCards(mCardLibrary.getRoundOneCard());
        }

        for (int i = 0; i < PLAYER_NUM; i++) {
            if (mPlayers[getActualIndex(i + mStartIndex)].askToBeBoss()) {
                mPlayers[getActualIndex(i + mStartIndex)].setRole(Player.Role.BOSS);
                mPlayers[getActualIndex(i + mStartIndex)].becomeBoss(mCardLibrary.getRoundTwoCard());
                mPlayers[getActualIndex(i + 1 + mStartIndex)].setRole(Player.Role.FARMER_NEXT);
                mPlayers[getActualIndex(i - 1 + mStartIndex)].setRole(Player.Role.FARMER_PRE);
                break;
            }
        }

        for (int i = 0; i < PLAYER_NUM; i++) {
            System.out.println(mPlayers[i].toString());
        }
    }

    int getActualIndex(int i) {
        if (i < 0) {
            return 0;
        }
        if (i >= PLAYER_NUM) {
            return i % PLAYER_NUM;
        }
        return i;
    }

}
