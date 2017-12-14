package com.duchen.poker.logic

import com.duchen.poker.model.CardGroup
import com.duchen.poker.model.type.CardGroupType
import com.duchen.poker.model.HandCardData
import com.duchen.poker.model.HandCardValue
import com.duchen.poker.model.type.TypeHelper

class HandCardLogic {

    fun getHandCardValue(handCardData: HandCardData): HandCardValue {
        val handCardValue = HandCardValue()

        if (handCardData.noMoreCard()) {
            handCardValue.mSumValue = 0
            handCardValue.mNeedRound = 0
            return handCardValue
        }

        val cardGroup = isOneCardGroup(handCardData.handCardList)
        val type = cardGroup.cardGroupType
        if (type !== CardGroupType.ERROR && type !== CardGroupType.FOUR_TAKE_ONE && type !== CardGroupType.FOUR_TAKE_TWO) {
            handCardValue.mSumValue = cardGroup.value
            handCardValue.mNeedRound = 1
            return handCardValue
        }

        val bestCardGroup = getPutCardGroup(handCardData)
        handCardData.putCards(bestCardGroup)

        val tmpValue = getHandCardValue(handCardData)

        handCardData.addCards(bestCardGroup)

        handCardValue.mSumValue = bestCardGroup.value + tmpValue.mSumValue
        handCardValue.mNeedRound = tmpValue.mNeedRound + 1

        return handCardValue
    }

    //若是一手牌，返回牌型，若不是一手牌，返回错误牌型。
    fun isOneCardGroup(handCardList: MutableList<Int>): CardGroup {
        val group = TypeHelper.instance.isOneCardGroup(handCardList)
        group.value = getCardGroupValue(group.cardGroupType, group.maxCard)
        return group
    }

    //获取最佳的一手出牌
    internal fun getPutCardGroup(handCardData: HandCardData): CardGroup {
        return CardGroup()
    }

    fun getCardGroupValue(type: CardGroupType, maxCard: Int): Int {
        //不出牌型
        return when {
            type === CardGroupType.ZERO -> 0
            type === CardGroupType.SINGLE -> maxCard - 10
            type === CardGroupType.DOUBLE -> maxCard - 10
            type === CardGroupType.THREE -> maxCard - 10
            type === CardGroupType.SINGLE_LINE -> maxCard - 10 + 1
            type === CardGroupType.DOUBLE_LINE -> maxCard - 10 + 1
            type === CardGroupType.THREE_LINE -> (maxCard - 3 + 1) / 2
            type === CardGroupType.THREE_TAKE_ONE -> maxCard - 10
            type === CardGroupType.THREE_TAKE_TWO -> maxCard - 10
            type === CardGroupType.THREE_TAKE_ONE_LINE -> (maxCard - 3 + 1) / 2
            type === CardGroupType.THREE_TAKE_TWO_LINE -> (maxCard - 3 + 1) / 2
            type === CardGroupType.FOUR_TAKE_ONE -> (maxCard - 3) / 2
            type === CardGroupType.FOUR_TAKE_TWO -> (maxCard - 3) / 2
            type === CardGroupType.BOMB_CARD -> maxCard - 3 + 7
            type === CardGroupType.KING_CARD -> 20
            else -> 0
        }
    }

}
