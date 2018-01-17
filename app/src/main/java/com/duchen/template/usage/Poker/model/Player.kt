package poker.model

import com.duchen.template.usage.Poker.model.PutCardDetail
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

    fun putCard(putStrategy: PutCardDetail.PutStrategy) : CardGroup {

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
