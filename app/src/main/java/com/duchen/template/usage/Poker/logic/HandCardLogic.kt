package poker.logic

import com.duchen.template.usage.Poker.model.TempSingleCardGroup
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

    //将这些牌按照最合理的方法进行分组
    fun getCardGroupList(handCardData: HandCardData): MutableList<CardGroup> {
        return getCardGroupList(handCardData.handCardList)
    }

    // http://blog.csdn.net/xdx2ct1314/article/details/18798263 这里计算轮数的思路
    fun getCardGroupList(handCardList: MutableList<Int>): MutableList<CardGroup> {
        val cardNumArr = IntArray(CardLibrary.CARD_ARR_SIZE)
        for (card in handCardList) {
            cardNumArr[card]++
        }
        var lineCount = 0
        val cardGroups = ArrayList<CardGroup>()
        if (cardNumArr[CardLibrary.JOKER1] != 0 && cardNumArr[CardLibrary.JOKER2] != 0) {
            cardGroups.add(getOneCardGroup(arrayListOf(CardLibrary.JOKER1, CardLibrary.JOKER2)))
            cardNumArr[CardLibrary.JOKER1] = 0
            cardNumArr[CardLibrary.JOKER2] = 0
        }

        //找到炸弹和三对
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_2) {
            if (cardNumArr[i] == 4) {
                cardGroups.add(getOneCardGroup(MutableList(4) { i }))
                cardNumArr[i] = 0
            } else if (cardNumArr[i] == 3) {
                cardGroups.add(getOneCardGroup(MutableList(3) { i }))
                cardNumArr[i] = 0
            }
        }

        //取对子
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
            if (cardNumArr[i] == 2) {
                cardGroups.add(getOneCardGroup(MutableList(2) { i }))
                cardNumArr[i] = 0
            }
        }

//        //取连对
//        lineCount = 0
//        for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
//            if (cardNumArr[i] == 2) {
//                lineCount++
//                if (i == CardLibrary.CARD_A && lineCount >= 3) {
//                    cardGroups.add(getOneCardGroup(MutableList(lineCount*2) { i-it%lineCount }))
//                    for (j in i+1-lineCount..i) {
//                        cardNumArr[j] -= 2
//                    }
//                }
//            } else {
//                if (lineCount >= 3) {
//                    cardGroups.add(getOneCardGroup(MutableList(lineCount*2) { i-1-it%lineCount }))
//                    for (j in i-lineCount until i) {
//                        cardNumArr[j] -= 2
//                    }
//                }
//                lineCount = 0
//            }
//        }

        //找到单顺
        lineCount = 0
        while (lineCount == 0) {
            for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
                if (cardNumArr[i] > 0) {
                    lineCount++
                    if (lineCount == 5) {
                        lineCount = 0
                        cardGroups.add(getOneCardGroup(MutableList(5) { i-it }))
                        cardNumArr.forEachIndexed { index, _ ->
                            if (index <= i && index > i - 5) {
                                cardNumArr[index]--
                            }
                        }
                        break
                    }
                } else {
                    if (i == CardLibrary.CARD_A) {
                        //退出单顺的寻找
                        lineCount = 1
                        break
                    } else {
                        lineCount = 0
                    }
                }
            }
        }

        //将三条和连对恢复回去
        var threeCards = cardGroups.filter { it.cardGroupType === CardGroupType.THREE }.map { it.maxCard }
//        var doubleLineCards = cardGroups.filter { it.cardGroupType === CardGroupType.DOUBLE_LINE }.flatMap {
//            doubleGroup -> MutableList(doubleGroup.cardList.size / 2) {
//                doubleGroup.maxCard - it
//            }
//        }
        var doubleCards = cardGroups.filter { it.cardGroupType === CardGroupType.DOUBLE }.map { it.maxCard }
        var singleLines = cardGroups.filter { it.cardGroupType === CardGroupType.SINGLE_LINE }

        threeCards.forEach { cardNumArr[it] += 3 }
        doubleCards.forEach { cardNumArr[it] += 2 }
        cardGroups.removeAll { it.cardGroupType === CardGroupType.THREE }
        cardGroups.removeAll { it.cardGroupType === CardGroupType.DOUBLE }

        //无中生有（通过三条或连对来组成单顺）
        lineCount = 0
        var tempSingleCardGroups = ArrayList<TempSingleCardGroup>()
        while (lineCount == 0) {
            var tempCardGroup : TempSingleCardGroup
            for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
                if (cardNumArr[i] > 0) {
                    lineCount++
                    if (lineCount >= 5) {
                        //如果后面是3个或2个的，则一直连成单顺
                        if (cardNumArr[i + 1] >= 2) {
                            continue
                        } else {
                            tempCardGroup = TempSingleCardGroup()
                            tempCardGroup.cardGroup = getOneCardGroup(MutableList(lineCount) { i-it })
                            cardNumArr.forEachIndexed { index, _ ->
                                if (index <= i && index > i - lineCount) {
                                    if (threeCards.contains(index)) {
                                        tempCardGroup.useThreeTypeCards.add(index)
                                    } else if (doubleCards.contains(index)) {
                                        tempCardGroup.useDoubleLineTypeCards.add(index)
                                    } else {
                                        tempCardGroup.helpSingleCards.add(index)
                                    }

                                    cardNumArr[index]--
                                }
                            }
                            tempSingleCardGroups.add(tempCardGroup)
                            lineCount = 0
                            break
                        }
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

        //告诉其他单顺，他们之间已经用了的牌
        if (tempSingleCardGroups.size > 1) {
            for (i in 0 until tempSingleCardGroups.size) {
                for (j in 0 until tempSingleCardGroups.size) {
                    if (i != j) {
                        tempSingleCardGroups[i].initAlsoValue(tempSingleCardGroups[j])
                    }
                }
            }
        }

        //todo 无中生有的顺子也要扩展
        //寻找剩余的牌加三条是否能组成新的单顺，这个单顺能使用到更多的单牌，没找到的话，需要恢复三条
        for (singleGroup in singleLines) {
            var tempCardGroup = TempSingleCardGroup()
            tempCardGroup.cardGroup = singleGroup
            var index = CardLibrary.CARD_3
            while (index <= CardLibrary.CARD_A) {
                if (cardNumArr[index] > 0 && tempCardGroup.appendCardToSingleLine(index)) {
                    cardNumArr[index]--
                    if (threeCards.contains(index)) {
                        tempCardGroup.hasUseOtherType = true
                        tempCardGroup.useThreeTypeCards.add(index)
                    } else if (doubleCards.contains(index)) {
                        tempCardGroup.hasUseOtherType = true
                        tempCardGroup.useDoubleLineTypeCards.add(index)
                    } else {
                        if (tempCardGroup.hasUseOtherType) {
                            tempCardGroup.helpSingleCards.add(index)
                        }
                    }
                    index = CardLibrary.CARD_3
                } else {
                    index++
                }
            }

            //告诉当前单顺，公用了哪些牌
            for (i in 0 until tempSingleCardGroups.size) {
                tempCardGroup.initAlsoValue(tempSingleCardGroups[i])
            }

            if (tempCardGroup.canHelp()) {
                //告诉其他单顺，已经有人用了这些牌了
                for (card in tempSingleCardGroups) {
                    card.initAlsoValue(tempCardGroup)
                }
            } else {
                tempCardGroup.useThreeTypeCards.forEach {
                    cardNumArr[it]++
                    tempCardGroup.removeCardFromSingleLine(it)
                }
                tempCardGroup.useDoubleLineTypeCards.forEach {
                    cardNumArr[it]++
                    tempCardGroup.removeCardFromSingleLine(it)
                }
                tempCardGroup.helpSingleCards.forEach {
                    cardNumArr[it]++
                    tempCardGroup.removeCardFromSingleLine(it)
                }
            }
        }

        //判断无中生有的单顺是否可行
        for (card in tempSingleCardGroups) {
            if (card.canHelp()) {
                cardGroups.add(card.cardGroup)
            } else {
                card.useThreeTypeCards.forEach {
                    cardNumArr[it]++
                }
                card.useDoubleLineTypeCards.forEach {
                    cardNumArr[it]++
                }
                card.helpSingleCards.forEach {
                    cardNumArr[it]++
                }
            }
        }

        for (i in CardLibrary.CARD_3..CardLibrary.CARD_2) {
            if (cardNumArr[i] == 3) {
                cardGroups.add(getOneCardGroup(MutableList(3) { i }))
                cardNumArr[i] = 0
            }
        }

        //取连对
        lineCount = 0
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
            if (cardNumArr[i] == 2) {
                lineCount++
                if (i == CardLibrary.CARD_A && lineCount >= 3) {
                    cardGroups.add(getOneCardGroup(MutableList(lineCount*2) { i-it%lineCount }))
                    for (j in i+1-lineCount..i) {
                        cardNumArr[j] -= 2
                    }
                }
            } else {
                if (lineCount >= 3) {
                    cardGroups.add(getOneCardGroup(MutableList(lineCount*2) { i-1-it%lineCount }))
                    for (j in i-lineCount until i) {
                        cardNumArr[j] -= 2
                    }
                }
                lineCount = 0
            }
        }

        //取对子
        for (i in CardLibrary.CARD_3..CardLibrary.CARD_2) {
            if (cardNumArr[i] == 2) {
                cardGroups.add(getOneCardGroup(MutableList(2) { i }))
                cardNumArr[i] -= 2
            }
        }

        //取单牌
        for (i in CardLibrary.CARD_3..CardLibrary.JOKER2) {
            if (cardNumArr[i] == 1) {
                cardGroups.add(getOneCardGroup(MutableList(1) { i }))
                cardNumArr[i] -= 1
            }
        }

        return cardGroups
    }

    private fun canHelp(singleCard : TempSingleCardGroup) : Boolean {
        return singleCard.helpSingleCards.size > singleCard.useThreeTypeCards.size + singleCard.useDoubleLineTypeCards.size
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
