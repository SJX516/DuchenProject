package com.duchen.poker.model

class Player {

    var role : Role
    internal var mHandCardData: HandCardData

    enum class Role {
        BOSS, FARMER_NEXT, FARMER_PRE
    }

    init {
        role = Role.BOSS
        mHandCardData = HandCardData()
    }

    fun askToBeBoss(): Boolean {
        return true
    }

    fun setCards(cards: MutableList<Int>) {
        mHandCardData.handCardList = cards
    }

    fun becomeBoss(threeCards: List<Int>) {
        mHandCardData.addCards(threeCards)
    }

    override fun toString(): String {
        return "Player{mRole=$role, mHandCardData=$mHandCardData}"
    }
}
