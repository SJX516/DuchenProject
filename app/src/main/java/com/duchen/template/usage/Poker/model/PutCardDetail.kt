package com.duchen.template.usage.Poker.model

import poker.model.CardGroup
import poker.model.Player

class PutCardDetail {

    var round : Int
    var role : Player.Role
    var cardGroup : CardGroup
    var putStrategy : PutStrategy

    enum class PutStrategy {
        INITIATIVE, PASSIVE
    }

    init {
        round = 0
        role = Player.Role.BOSS
        cardGroup = CardGroup()
        putStrategy = PutStrategy.INITIATIVE
    }
}
