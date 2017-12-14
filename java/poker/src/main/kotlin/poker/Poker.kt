package com.duchen.poker

import com.duchen.poker.model.Player

import java.util.Random

class Poker {

    internal var mPlayers = arrayOfNulls<Player>(PLAYER_NUM)
    internal var mCardLibrary = CardLibrary()
    internal var mStartIndex = 0

    fun startNewGame() {
        mCardLibrary = CardLibrary()
        mStartIndex = Random().nextInt(PLAYER_NUM)

        for (i in 0 until PLAYER_NUM) {
            mPlayers[getActualIndex(i + mStartIndex)] = Player()
            mPlayers[getActualIndex(i + mStartIndex)]!!.setCards(mCardLibrary.roundOneCard)
        }

        for (i in 0 until PLAYER_NUM) {
            if (mPlayers[getActualIndex(i + mStartIndex)]!!.askToBeBoss()) {
                mPlayers[getActualIndex(i + mStartIndex)]!!.role = Player.Role.BOSS
                mPlayers[getActualIndex(i + mStartIndex)]!!.becomeBoss(mCardLibrary.roundTwoCard)
                mPlayers[getActualIndex(i + 1 + mStartIndex)]!!.role = Player.Role.FARMER_NEXT
                mPlayers[getActualIndex(i - 1 + mStartIndex)]!!.role = Player.Role.FARMER_PRE
                break
            }
        }

        for (i in 0 until PLAYER_NUM) {
            println(mPlayers[i].toString())
        }
    }

    internal fun getActualIndex(i: Int): Int {
        if (i < 0) {
            return 0
        }
        return if (i >= PLAYER_NUM) {
            i % PLAYER_NUM
        } else i
    }

    companion object {
        var PLAYER_NUM = 3
    }

}
