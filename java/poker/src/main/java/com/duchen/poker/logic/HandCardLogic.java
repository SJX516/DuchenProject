package com.duchen.poker.logic;

import com.duchen.poker.model.CardGroup;
import com.duchen.poker.model.type.CardGroupType;
import com.duchen.poker.model.HandCardData;
import com.duchen.poker.model.HandCardValue;
import com.duchen.poker.model.type.TypeHelper;

import java.util.List;

public class HandCardLogic {

    public HandCardValue getHandCardValue(HandCardData handCardData) {
        HandCardValue handCardValue = new HandCardValue();

        if (handCardData.noMoreCard()) {
            handCardValue.mSumValue = 0;
            handCardValue.mNeedRound = 0;
            return handCardValue;
        }

        CardGroup cardGroup = isOneCardGroup(handCardData.getHandCardList());
        CardGroupType type = cardGroup.getCardGroupType();
        if (type != CardGroupType.ERROR && type != CardGroupType.FOUR_TAKE_ONE && type != CardGroupType.FOUR_TAKE_TWO) {
            handCardValue.mSumValue = cardGroup.getValue();
            handCardValue.mNeedRound = 1;
            return handCardValue;
        }

        CardGroup bestCardGroup = getPutCardGroup(handCardData);
        handCardData.putCards(bestCardGroup);

        HandCardValue tmpValue = getHandCardValue(handCardData);

        handCardData.addCards(bestCardGroup);

        handCardValue.mSumValue = bestCardGroup.getValue() + tmpValue.mSumValue;
        handCardValue.mNeedRound = tmpValue.mNeedRound + 1;

        return handCardValue;
    }

    //若是一手牌，返回牌型，若不是一手牌，返回错误牌型。
    public CardGroup isOneCardGroup(List<Integer> handCardList) {
        CardGroup group = TypeHelper.getInstance().isOneCardGroup(handCardList);
        group.setValue(getCardGroupValue(group.getCardGroupType(), group.getMaxCard()));
        return group;
    }

    //获取最佳的一手出牌
    CardGroup getPutCardGroup(HandCardData handCardData) {
        return new CardGroup();
    }

    public int getCardGroupValue(CardGroupType type, int maxCard) {
        //不出牌型
        if (type == CardGroupType.ZERO) return 0;
            //单牌类型
        else if (type == CardGroupType.SINGLE) return maxCard - 10;
            //对牌类型
        else if (type == CardGroupType.DOUBLE) return maxCard - 10;
            //三条类型
        else if (type == CardGroupType.THREE) return maxCard - 10;
            //单连类型
        else if (type == CardGroupType.SINGLE_LINE) return maxCard - 10 + 1;
            //对连类型
        else if (type == CardGroupType.DOUBLE_LINE) return maxCard - 10 + 1;
            //三连类型
        else if (type == CardGroupType.THREE_LINE) return (maxCard - 3 + 1)/2;
            //三带一单
        else if (type == CardGroupType.THREE_TAKE_ONE) return maxCard - 10;
            //三带一对
        else if (type == CardGroupType.THREE_TAKE_TWO) return maxCard - 10;
            //三带一单连
        else if (type == CardGroupType.THREE_TAKE_ONE_LINE) return (maxCard - 3 + 1)/2;
            //三带一对连
        else if (type == CardGroupType.THREE_TAKE_TWO_LINE) return (maxCard - 3 + 1)/2;
            //四带两单
        else if (type == CardGroupType.FOUR_TAKE_ONE) return (maxCard - 3)/2;
            //四带两对
        else if (type == CardGroupType.FOUR_TAKE_TWO) return (maxCard - 3)/2;
            //炸弹类型
        else if (type == CardGroupType.BOMB_CARD) return maxCard - 3 + 7;
            //王炸类型
        else if (type == CardGroupType.KING_CARD) return 20;
            //错误牌型
        else return 0;
    }

}
