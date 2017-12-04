package com.duchen.poker.model;

import java.util.ArrayList;
import java.util.List;

public class CardGroup {

    CardGroupType mCardGroupType = CardGroupType.ERROR;
    int mCount = 0;
    List<Integer> mCardList = new ArrayList<>();
    //该牌型中用于决定大小的值（比如33344中的3）
    int mMaxCard = 0;
    int mValue = 0;

    public int getCardGroupValue(CardGroupType type, int maxCard, int count) {
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
