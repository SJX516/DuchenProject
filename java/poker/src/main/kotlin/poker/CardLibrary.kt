package poker

import java.util.ArrayList
import java.util.Random

class CardLibrary {

    internal var mCards: IntArray
    internal var mRandom = Random()

    val roundOneCard: MutableList<Int>
        get() {
            val cards = ArrayList<Int>()
            for (i in 0..16) {
                cards.add(oneCard)
            }
            return cards
        }

    val roundTwoCard: List<Int>
        get() {
            val cards = ArrayList<Int>()
            for (i in 0..2) {
                cards.add(oneCard)
            }
            return cards
        }

    val oneCard: Int
        get() {
            var start = random()
            if (start < 0 || start > mCards.size) {
                start = 3
            }
            for (i in mCards.indices) {
                var actualIndex = i + start
                if (actualIndex >= mCards.size) {
                    actualIndex %= mCards.size
                }

                if (mCards[actualIndex] > 0) {
                    mCards[actualIndex]--
                    return actualIndex
                }
            }
            return -1
        }

    init {
        mCards = IntArray(18)
        for (i in 3..15) {
            mCards[i] = 4
        }
        mCards[16] = 1
        mCards[17] = 1
    }

    internal fun random(): Int {
        return mRandom.nextInt(18)
    }

    companion object {
        val CARD_ARR_SIZE = 18
        val JOKER1 = 16
        val JOKER2 = 17
        val CARD_3 = 3
        val CARD_2 = 15

        fun getCardChar(i: Int): String {
            if (i in 3..10) {
                return i.toString() + ""
            } else {
                when (i) {
                    11 -> return "J"
                    12 -> return "Q"
                    13 -> return "K"
                    14 -> return "A"
                    15 -> return "2"
                    16 -> return "小王"
                    17 -> return "大王"
                }
            }
            return "ERROR" + i
        }
    }

}
