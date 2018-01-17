package poker

import com.duchen.template.usage.Poker.PrintUtil
import poker.logic.HandCardLogic
import poker.model.Player

import java.util.Random

class Poker {

    var mPlayers = ArrayList<Player>()
    var mCardLibrary = CardLibrary()
    var mLogic = HandCardLogic()
    var mStartIndex = 0
    var mBossIndex = 0

    val mLeftCardNumArr = IntArray(CardLibrary.CARD_ARR_SIZE)


    fun startNewGame() {
        mCardLibrary = CardLibrary()
        mStartIndex = Random().nextInt(PLAYER_NUM)

        for (i in 0 until PLAYER_NUM) {
            mPlayers.add(Player())
        }

        for (i in 0 until 17) {
            mPlayers.forEachFromIndex(mStartIndex) {
                it, _ ->
                it.addCard(mCardLibrary.oneCard)
                true
            }
        }

        mPlayers.forEachFromIndex(mStartIndex) {
            it, index ->
            if (it.askToBeBoss()) {
                mBossIndex = index
                mPlayers[mBossIndex].role = Player.Role.BOSS
                mPlayers[mBossIndex].addCards(mCardLibrary.roundTwoCard)
                mPlayers.getInLoop(mBossIndex + 1).role = Player.Role.FARMER_NEXT
                mPlayers.getInLoop(mBossIndex - 1).role = Player.Role.FARMER_PRE
                false
            } else {
                true
            }
        }

        println()
        mPlayers.forEachFromIndex(mBossIndex) {
            it, _ ->
            println(it.toString())
            val groups = mLogic.getCardGroupList(it.handCardData)
            PrintUtil.printCardGroups(it.handCardData.handCardList.sorted(), groups)
            true
        }


    }

    fun <T> List<T>.forEachFromIndex(start : Int, action: (T, Int) -> Boolean) {
        for (i in 0 until this.size) {
            val index = this.getIndexInLoop(start + i)
            if (!action(this[index], index)) {
                break
            }
        }
    }

    fun <T> List<T>.getInLoop(index : Int) : T {
        return this[this.getIndexInLoop(index)]
    }

    fun <T> List<T>.getIndexInLoop(index : Int) : Int {
        var realIndex = index
        while (realIndex < 0) {
            realIndex += this.size
        }
        if (realIndex >= this.size) {
            realIndex %= this.size
        }
        return realIndex
    }

    companion object {
        var PLAYER_NUM = 3
    }

}
