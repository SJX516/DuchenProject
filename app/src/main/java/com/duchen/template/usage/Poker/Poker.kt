package poker

import com.duchen.template.usage.Poker.GameStatus
import com.duchen.template.usage.Poker.PrintUtil
import com.duchen.template.usage.Poker.model.PutCardDetail
import poker.logic.HandCardLogic
import poker.model.CardGroup
import poker.model.Player

import java.util.Random

class Poker {

    var mPlayers = ArrayList<Player>()
    var mCardLibrary = CardLibrary()
    var mGameStatus = GameStatus()
    var mStartIndex = 0
    var mBossIndex = 0


    fun startNewGame() {
        mCardLibrary = CardLibrary()
        mGameStatus = GameStatus()
        mStartIndex = Random().nextInt(PLAYER_NUM)

        for (i in 0 until PLAYER_NUM) {
            mPlayers.add(Player())
        }

        //每人17张牌
        for (i in 0 until 17) {
            mPlayers.forEachFromIndex(mStartIndex) {
                it, _ ->
                it.addCard(mCardLibrary.oneCard)
                true
            }
        }

        //叫地主，叫完之后分配角色，摸牌
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
            val groups = HandCardLogic.getCardGroupList(it.handCardData)
            PrintUtil.printCardGroups(it.handCardData.handCardList.sorted(), groups)
            true
        }

        var currentRound = 0
        var noWinner = true
        while (noWinner) {
            mPlayers.forEachFromIndex(mBossIndex) {
                it, _ ->
                val putStrategy = mGameStatus.getCurrentPutStrategy()
                if (putStrategy === PutCardDetail.PutStrategy.INITIATIVE) {
                    currentRound++
                    PrintUtil.printNewRound(currentRound)
                }
                val group = it.putCard(putStrategy, mGameStatus)
                val cardDetail = PutCardDetail(currentRound, it.role,
                        group, putStrategy)
                mGameStatus.put(cardDetail)
                PrintUtil.printPutCard(cardDetail)
                if (it.noMoreCards()) {
                    PrintUtil.win(it)
                    noWinner = false
                    false
                } else {
                    true
                }
            }
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
