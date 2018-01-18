package poker

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
        mCards = IntArray(CARD_ARR_SIZE)
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
        val CARD_3 = 3
        val CARD_A = 14
        val CARD_2 = 15
        val JOKER1 = 16
        val JOKER2 = 17
        val CARD_ARR_SIZE = 18

        fun getCardChar(i: Int): String {
            if (i in 3..9) {
                return i.toString() + ""
            } else {
                when (i) {
                    10 -> return "X"
                    11 -> return "J"
                    12 -> return "Q"
                    13 -> return "K"
                    14 -> return "A"
                    15 -> return "2"
                    16 -> return "I"
                    17 -> return "L"
                }
            }
            return "ERROR" + i
        }

        fun getCardList(str: String): MutableList<Int> {
            val list = ArrayList<Int>()
            str.forEach {
                when(it) {
                    '3' -> list.add(3)
                    '4' -> list.add(4)
                    '5' -> list.add(5)
                    '6' -> list.add(6)
                    '7' -> list.add(7)
                    '8' -> list.add(8)
                    '9' -> list.add(9)
                    'X' -> list.add(10)
                    'J' -> list.add(11)
                    'Q' -> list.add(12)
                    'K' -> list.add(13)
                    'A' -> list.add(14)
                    '2' -> list.add(15)
                    'I' -> list.add(16)
                    'L' -> list.add(17)
                }
            }
            return list
        }
    }

}
