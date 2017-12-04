package com.duchen.poker.model;

public enum CardGroupType {
    ERROR,                 //错误类型
    ZERO,                   //不出类型
    SINGLE,                 //单牌类型
    DOUBLE,                 //对牌类型
    THREE,                  //三条类型
    SINGLE_LINE,            //单连类型
    DOUBLE_LINE,            //对连类型
    THREE_LINE,             //三连类型
    THREE_TAKE_ONE,         //三带一单
    THREE_TAKE_TWO,         //三带一对
    THREE_TAKE_ONE_LINE,    //三带一单连
    THREE_TAKE_TWO_LINE,   //三带一对连
    FOUR_TAKE_ONE,         //四带两单
    FOUR_TAKE_TWO,         //四带两对
    BOMB_CARD,             //炸弹类型
    KING_CARD              //王炸类型
}
