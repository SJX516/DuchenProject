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

    fun getCardGroupList(handCardData: HandCardData): MutableList<CardGroup> {
        return getCardGroupList(handCardData.handCardList)
    }

    // http://blog.csdn.net/xdx2ct1314/article/details/18798263 这里计算轮数的思路
    fun getCardGroupList(handCardList: MutableList<Int>): MutableList<CardGroup> {
        val cardNumArr = IntArray(CardLibrary.CARD_ARR_SIZE)
        for (card in handCardList) {
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
            for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
                if (cardNumArr[i] > 0) {
                    lineCount++
                    if (lineCount == 5) {
                        lineCount = 0
                        cardGroups.add(getOneCardGroup(MutableList(5) { cardNumArr[i-it] }))
                        cardNumArr.forEachIndexed { index, _ ->
                            if (index <= i && index > i - 5) {
                                cardNumArr[index]--
                            }
                        }
                        break
                    }
                } else {
                    if (i == CardLibrary.CARD_A) {
                        lineCount = 1
                        break
                    } else {
                        lineCount = 0
                    }
                }
            }
        }

        //寻找剩余的牌加三条是否能组成新的单顺，这个单顺能使用到更多的单牌，没找到的话，需要恢复三条
        var threeCards = cardGroups.filter { it.cardGroupType === CardGroupType.THREE }.map { it.maxCard }
        var singleLines = cardGroups.filter { it.cardGroupType === CardGroupType.SINGLE_LINE }

        threeCards.forEach { cardNumArr[it] += 3 }
        cardGroups.removeIf { it.cardGroupType === CardGroupType.THREE }

        for (singleGroup in singleLines) {
            var useThreeTypeCards = ArrayList<Int>()
            var helpSingleCards = ArrayList<Int>()
            var index = CardLibrary.CARD_3
            while (index <= CardLibrary.CARD_A) {
                if (cardNumArr[index] > 0 && singleGroup.appendCardToSingleLine(index)) {
                    cardNumArr[index]--
                    if (threeCards.contains(index)) {
                        useThreeTypeCards.add(index)
                    } else {
                        helpSingleCards.add(index)
                    }
                    index = CardLibrary.CARD_3
                } else {
                    index++
                }
            }
            if (helpSingleCards.size <= useThreeTypeCards.size) {
                useThreeTypeCards.forEach {
                    cardNumArr[it]++
                    singleGroup.removeCardFromSingleLine(it)
                }
                helpSingleCards.forEach {
                    cardNumArr[it]++
                    singleGroup.removeCardFromSingleLine(it)
                }
            }
        }

        for (i in CardLibrary.CARD_3..CardLibrary.CARD_2) {
            if (cardNumArr[i] == 3) {
                cardGroups.add(getOneCardGroup(MutableList(3) { cardNumArr[i] }))
                cardNumArr[i] = 0
            }
        }

        //取双顺
        lineCount = 0
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
            if (cardNumArr[i] == 2) {
                lineCount++
                if (i == CardLibrary.CARD_A && lineCount >= 3) {
                    cardGroups.add(getOneCardGroup(MutableList(lineCount*2) { cardNumArr[i- it % lineCount] }))
                    cardNumArr.forEachIndexed { index, _ ->
                        if (index <= i && index > i - lineCount) {
                            cardNumArr[index] -= 2
                        }
                    }
                }
            } else {
                if (lineCount >= 3) {
                    cardGroups.add(getOneCardGroup(MutableList(lineCount*2) { cardNumArr[i- it % lineCount] }))
                    cardNumArr.forEachIndexed { index, _ ->
                        if (index <= i && index > i - lineCount) {
                            cardNumArr[index] -= 2
                        }
                    }
                }
                lineCount = 0
            }
        }

        //取对子
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
            if (cardNumArr[i] == 2) {
                cardGroups.add(getOneCardGroup(MutableList(2) { cardNumArr[i] }))
                cardNumArr[i] -= 2
            }
        }

        //取单牌
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
            if (cardNumArr[i] == 1) {
                cardGroups.add(getOneCardGroup(MutableList(1) { cardNumArr[i] }))
                cardNumArr[i] -= 1
            }
        }

        return cardGroups
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
