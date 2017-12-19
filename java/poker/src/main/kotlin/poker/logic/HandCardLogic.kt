package poker.logic

import poker.CardLibrary
import poker.model.CardGroup
import poker.model.type.CardGroupType
import poker.model.HandCardData
import poker.model.HandCardValue
import poker.model.type.TypeHelper
import java.util.*
import kotlin.collections.ArrayList

class HandCardLogic {

    fun initHandCardData(handCardData: HandCardData) {
        val handCardValue = HandCardValue()
        if (handCardData.noMoreCard()) {
            handCardValue.mSumValue = 0
            handCardValue.mNeedRound = 0
            handCardData.handCardValue = handCardValue
            return
        }

        val cardGroup = getOneCardGroup(handCardData.handCardList)
        val type = cardGroup.cardGroupType
        if (type !== CardGroupType.ERROR && type !== CardGroupType.FOUR_TAKE_ONE && type !== CardGroupType.FOUR_TAKE_TWO) {
            handCardValue.mSumValue = cardGroup.value
            handCardValue.mNeedRound = 1
            handCardData.handCardValue = handCardValue
            handCardData.setCardGroups(Arrays.asList(cardGroup))
            return
        }

        val cardGroups = getCardGroupList(handCardData)
        handCardValue.mSumValue = cardGroups.sumBy { getCardGroupValue(it.cardGroupType, it.maxCard) }
        handCardValue.mNeedRound = cardGroups.size
        handCardData.handCardValue = handCardValue
        handCardData.setCardGroups(cardGroups)
    }

    // http://blog.csdn.net/xdx2ct1314/article/details/18798263 这里计算轮数的思路
    fun getCardGroupList(handCardData: HandCardData): MutableList<CardGroup> {
        val cardNumArr = IntArray(CardLibrary.CARD_ARR_SIZE)
        for (card in handCardData.handCardList) {
            cardNumArr[card]++
        }

        val cardGroups = ArrayList<CardGroup>()
        if (cardNumArr[CardLibrary.JOKER1] != 0 && cardNumArr[CardLibrary.JOKER2] != 0) {
            cardGroups.add(getOneCardGroup(arrayListOf(CardLibrary.JOKER1, CardLibrary.JOKER2)))
            cardNumArr[CardLibrary.JOKER1] = 0
            cardNumArr[CardLibrary.JOKER2] = 0
        }
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_2) {
            if (cardNumArr[i] == 4) {
                cardGroups.add(getOneCardGroup(MutableList(4) { cardNumArr[i] }))
                cardNumArr[i] = 0
            } else if (cardNumArr[i] == 3) {
                cardGroups.add(getOneCardGroup(MutableList(3) { cardNumArr[i] }))
                cardNumArr[i] = 0
            }
        }

        var lineCount = 0
        while (lineCount == 0) {
            for (i in CardLibrary.CARD_3..CardLibrary.CARD_2) {
                if (cardNumArr[i] > 0) {
                    lineCount++
                    if (lineCount == 5) {
                        lineCount = 0
                        cardGroups.add(getOneCardGroup(MutableList(5) { cardNumArr[i-it] }))
                        cardNumArr.forEachIndexed { index, _ ->
                            if (index <= i && index >= i - 4) {
                                cardNumArr[index]--
                            }
                        }
                        break
                    }
                } else {
                    if (lineCount != 0) {
                        break
                    }
                }
            }
        }
    }

    fun getHandCardValue(handCardData: HandCardData): HandCardValue {
        val handCardValue = HandCardValue()

        if (handCardData.noMoreCard()) {
            handCardValue.mSumValue = 0
            handCardValue.mNeedRound = 0
            return handCardValue
        }

        val cardGroup = getOneCardGroup(handCardData.handCardList)
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
    fun getOneCardGroup(handCardList: MutableList<Int>): CardGroup {
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
