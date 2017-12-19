package poker

import poker.model.Player

import java.util.Random

class Poker {

    var mPlayers = arrayOfNulls<Player>(PLAYER_NUM)
    var mCardLibrary = CardLibrary()
    var mStartIndex = 0
    var mBossIndex = 0

    fun startNewGame() {
        mCardLibrary = CardLibrary()
        mStartIndex = Random().nextInt(PLAYER_NUM)

        for (i in 0 until PLAYER_NUM) {
            mPlayers[getActualIndex(i + mStartIndex)] = Player()
        }

        for (i in 0 until 17) {
            for (j in 0 until PLAYER_NUM) {
                mPlayers[getActualIndex(j + mStartIndex)]!!.addCard(mCardLibrary.oneCard)
            }
        }

        for (i in 0 until PLAYER_NUM) {
            if (mPlayers[getActualIndex(i + mStartIndex)]!!.askToBeBoss()) {
                mBossIndex = getActualIndex(i + mStartIndex)
                mPlayers[mBossIndex]!!.role = Player.Role.BOSS
                mPlayers[mBossIndex]!!.addCards(mCardLibrary.roundTwoCard)
                mPlayers[getActualIndex(mBossIndex + 1)]!!.role = Player.Role.FARMER_NEXT
                mPlayers[getActualIndex(mBossIndex - 1)]!!.role = Player.Role.FARMER_PRE
                break
            }
        }

        for (i in 0 until PLAYER_NUM) {
            println(mPlayers[i].toString())
        }

    }

    private fun getActualIndex(i: Int): Int {
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
