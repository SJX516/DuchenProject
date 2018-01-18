package com.duchen.template.usage.Poker

import com.duchen.template.usage.Poker.model.PutCardDetail
import poker.CardLibrary
import poker.model.Player
import poker.model.type.CardGroupType

class GameStatus {

    var mCurrentRound = 0
    val mPutCardList = ArrayList<PutCardDetail>()
    val mLeftCardNumArr = IntArray(CardLibrary.CARD_ARR_SIZE)

    init {
        for (i in 3..15) {
            mLeftCardNumArr[i] = 4
        }
        mLeftCardNumArr[16] = 1
        mLeftCardNumArr[17] = 1
    }

    fun getCurrentPutStrategy(): PutCardDetail.PutStrategy {
        if (mPutCardList.size == 0) {
            return PutCardDetail.PutStrategy.INITIATIVE
        } else if (mPutCardList.size < 3) {
            return PutCardDetail.PutStrategy.PASSIVE
        } else {
            if (mPutCardList[mPutCardList.size - 1].cardGroup.cardGroupType === CardGroupType.ZERO
                    && mPutCardList[mPutCardList.size - 2].cardGroup.cardGroupType === CardGroupType.ZERO) {
                return PutCardDetail.PutStrategy.INITIATIVE
            } else {
                return PutCardDetail.PutStrategy.PASSIVE
            }
        }
    }

    fun put(detail: PutCardDetail) {
        detail.cardGroup.cardList.forEach {
            mLeftCardNumArr[it]--
        }
        mPutCardList.add(detail)
    }

}