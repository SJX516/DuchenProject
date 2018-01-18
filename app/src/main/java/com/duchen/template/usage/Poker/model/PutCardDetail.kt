package com.duchen.template.usage.Poker.model

import poker.model.CardGroup
import poker.model.Player

class PutCardDetail {

    var round : Int
    var role : Player.Role
    var cardGroup : CardGroup
    var putStrategy : PutStrategy

    constructor(round: Int, role: Player.Role, cardGroup: CardGroup, putStrategy: PutStrategy) {
        this.round = round
        this.role = role
        this.cardGroup = cardGroup
        this.putStrategy = putStrategy
    }

    enum class PutStrategy {
        INITIATIVE, PASSIVE
    }

}
