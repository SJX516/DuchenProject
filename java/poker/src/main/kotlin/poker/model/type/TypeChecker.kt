package com.duchen.poker.model.type

interface TypeChecker {

    val type: CardGroupType

    val myPossibleCardCount: List<Int>

    fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int

}
