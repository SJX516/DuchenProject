package poker.model

import com.duchen.template.usage.Poker.GameStatus
import com.duchen.template.usage.Poker.model.PutCardDetail
import poker.logic.HandCardLogic
import java.util.*

class Player {

    var role : Role
    var handCardData: HandCardData

    enum class Role {
        BOSS, FARMER_NEXT, FARMER_PRE
    }

    init {
        role = Role.BOSS
        handCardData = HandCardData()
    }

    fun askToBeBoss(): Boolean {
        return true
    }

    fun putCard(putStrategy: PutCardDetail.PutStrategy, gameStatus: GameStatus) : CardGroup {
        if (handCardData.handCardGroupList.size == 0) {
            HandCardLogic.initHandCardData(handCardData)
        }
        return handCardData.handCardGroupList.removeAt(0)
    }

    fun noMoreCards(): Boolean {
        return handCardData.handCardGroupList.size == 0
    }

    fun setCards(cards: MutableList<Int>) {
        handCardData.handCardList = cards
    }

    fun addCard(card: Int) {
        handCardData.addCards(Arrays.asList(card))
    }

    fun addCards(threeCards: List<Int>) {
        handCardData.addCards(threeCards)
    }

    override fun toString(): String {
        return "Player{mRole=$role, handCardData=$handCardData}"
    }
}
