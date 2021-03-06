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

    companion object {

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
                        if (i == CardLibrary.CARD_A && lineCount >= 5) {
                            cardGroups.add(getOneCardGroup(MutableList(lineCount) { i - it }))
                            cardNumArr.forEachIndexed { index, _ ->
                                if (index <= i && index > i - lineCount) {
                                    cardNumArr[index]--
                                }
                            }
                            lineCount = 0
                            break
                        }
                    } else {
                        if (lineCount >= 5) {
                            cardGroups.add(getOneCardGroup(MutableList(lineCount) { i - 1 - it }))
                            cardNumArr.forEachIndexed { index, _ ->
                                if (index <= i - 1 && index > i - 1 - lineCount) {
                                    cardNumArr[index]--
                                }
                            }
                            lineCount = 0
                            break
                        } else if (i == CardLibrary.CARD_A) {
                            //遍历一遍，count不够时，直接退出
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
                var tempCardGroup: TempSingleCardGroup
                for (i in CardLibrary.CARD_3..CardLibrary.CARD_A) {
                    if (cardNumArr[i] > 0) {
                        lineCount++
                        if (lineCount >= 5) {
                            //如果后面是3个或2个的，则一直连成单顺
                            if (cardNumArr[i + 1] >= 2 && CardLibrary.CARD_A - i - 1 >= 5) {
                                continue
                            } else {
                                tempCardGroup = TempSingleCardGroup()
                                tempCardGroup.cardGroup = getOneCardGroup(MutableList(lineCount) { i - it })
                                cardNumArr.forEachIndexed { index, _ ->
                                    if (index <= i && index > i - lineCount) {
                                        if (threeCards.contains(index)) {
                                            tempCardGroup.useThreeTypeCards.add(index)
                                        } else if (doubleCards.contains(index)) {
                                            tempCardGroup.useDoubleTypeCards.add(index)
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

            for (singleGroup in singleLines) {
                var tempCardGroup = TempSingleCardGroup()
                tempCardGroup.cardGroup = singleGroup
                tempCardGroup.isLineFromNone = false
                tempSingleCardGroups.add(tempCardGroup)
            }

            //寻找剩余的牌加三条是否能组成新的单顺，这个单顺能使用到更多的单牌，没找到的话，需要恢复三条
            for (tempCardGroup in tempSingleCardGroups) {
                var index = CardLibrary.CARD_3
                while (index <= CardLibrary.CARD_A) {
                    if (cardNumArr[index] > 0 && tempCardGroup.appendCardToSingleLine(index)) {
                        cardNumArr[index]--
                        if (threeCards.contains(index)) {
                            tempCardGroup.hasUseOtherTypeEXT = true
                            tempCardGroup.useThreeTypeCardsEXT.add(index)
                        } else if (doubleCards.contains(index)) {
                            tempCardGroup.hasUseOtherTypeEXT = true
                            tempCardGroup.useDoubleTypeCardsEXT.add(index)
                        } else {
                            if (tempCardGroup.hasUseOtherTypeEXT) {
                                tempCardGroup.helpSingleCardsEXT.add(index)
                            } else {
                                tempCardGroup.helpSingleCards.add(index)
                            }
                        }
                        index = CardLibrary.CARD_3
                    } else {
                        index++
                    }
                }
            }

            //检查无中生有是否有效
            var totalUsedCardsWithNotCount = ArrayList<Int>()
            var totalUsedCards = ArrayList<Int>()
            var totalHelpedCards = ArrayList<Int>()
            for (cardGroup in tempSingleCardGroups) {
                for (threeCard in cardGroup.useThreeTypeCards) {
                    if (!totalUsedCards.contains(threeCard)) {
                        totalUsedCardsWithNotCount.add(threeCard)
                        totalUsedCards.add(threeCard)
                    }
                }
                for (twoCard in cardGroup.useDoubleTypeCards) {
                    if (!totalUsedCards.contains(twoCard)) {
                        totalUsedCardsWithNotCount.add(twoCard)
                        totalUsedCards.add(twoCard)
                    } else {
                        totalUsedCards.remove(twoCard)
                    }
                }
                for (twoCard in cardGroup.useDoubleTypeCardsEXT) {
                    if (totalUsedCards.contains(twoCard)) {
                        totalUsedCards.remove(twoCard)
                    }
                }
                totalHelpedCards.addAll(cardGroup.helpSingleCards)
            }
            var getLineFromNoneSucc = false
            if (totalUsedCards.size < totalHelpedCards.size) {
                //无中生有有效
                getLineFromNoneSucc = true
                for (cardGroup in tempSingleCardGroups) {
                    if (cardGroup.isLineFromNone) {
                        cardGroups.add(cardGroup.cardGroup)
                    }
                }
            } else {
                val removeCardGroups = ArrayList<TempSingleCardGroup>()
                for (cardGroup in tempSingleCardGroups) {
                    if (cardGroup.isLineFromNone) {
                        removeCardGroups.add(cardGroup)
                        cardGroup.release().forEach {
                            cardNumArr[it]++
                        }
                    }
                }
                tempSingleCardGroups.removeAll(removeCardGroups)
            }

            //处理正常扩展的有效性
            for (cardGroup in tempSingleCardGroups) {
                var singleUsedCards = ArrayList<Int>()
                if (getLineFromNoneSucc) {
                    for (threeCard in cardGroup.useThreeTypeCardsEXT) {
                        if (!totalUsedCardsWithNotCount.contains(threeCard)) {
                            singleUsedCards.add(threeCard)
                        }
                    }
                    for (twoCard in cardGroup.useDoubleTypeCardsEXT) {
                        if (!totalUsedCardsWithNotCount.contains(twoCard)) {
                            singleUsedCards.add(twoCard)
                        }
                    }
                } else {
                    singleUsedCards.addAll(cardGroup.useThreeTypeCardsEXT)
                    singleUsedCards.addAll(cardGroup.useDoubleTypeCardsEXT)
                }
                if ((cardGroup.helpSingleCardsEXT.size > singleUsedCards.size) || singleUsedCards.size == 0) {
                    //扩展成功
                } else {
                    cardGroup.useThreeTypeCardsEXT.forEach {
                        cardNumArr[it]++
                        cardGroup.removeCardFromSingleLine(it)
                    }
                    cardGroup.useDoubleTypeCardsEXT.forEach {
                        cardNumArr[it]++
                        cardGroup.removeCardFromSingleLine(it)
                    }
                    cardGroup.helpSingleCardsEXT.forEach {
                        cardNumArr[it]++
                        cardGroup.removeCardFromSingleLine(it)
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
                        cardGroups.add(getOneCardGroup(MutableList(lineCount * 2) { i - it % lineCount }))
                        for (j in i + 1 - lineCount..i) {
                            cardNumArr[j] -= 2
                        }
                    }
                } else {
                    if (lineCount >= 3) {
                        cardGroups.add(getOneCardGroup(MutableList(lineCount * 2) { i - 1 - it % lineCount }))
                        for (j in i - lineCount until i) {
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


        //若是一手牌，返回牌型，若不是一手牌，返回错误牌型。
        fun getOneCardGroup(handCardList: MutableList<Int>): CardGroup {
            val group = TypeHelper.instance.isOneCardGroup(handCardList)
            group.value = getCardGroupValue(group.cardGroupType, group.maxCard)
            return group
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

        //获取最佳的一手出牌
        internal fun getPutCardGroup(handCardData: HandCardData): CardGroup {
            return CardGroup()
        }
    }
}
