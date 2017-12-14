package com.duchen.poker.model.type

enum class CardGroupType {
    ERROR, //错误类型
    ZERO, //不出类型
    SINGLE, //单牌类型  3
    DOUBLE, //对牌类型  33
    THREE, //三条类型  333
    SINGLE_LINE, //单连类型  34567
    DOUBLE_LINE, //对连类型  334455
    THREE_LINE, //三连类型  333444
    THREE_TAKE_ONE, //三带一单  3334
    THREE_TAKE_TWO, //三带一对  33344
    THREE_TAKE_ONE_LINE, //三带一单连 33344479
    THREE_TAKE_TWO_LINE, //三带一对连  33344488JJ
    FOUR_TAKE_ONE, //四带两单
    FOUR_TAKE_TWO, //四带两对
    BOMB_CARD, //炸弹类型
    KING_CARD  //王炸类型
}
