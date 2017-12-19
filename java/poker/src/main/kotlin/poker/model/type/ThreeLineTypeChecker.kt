package poker.model.type

import java.util.Arrays

class ThreeLineTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.THREE_LINE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(6, 9, 12, 15, 18)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val cardCountArray = IntArray(18)
        for (card in cards) {
            cardCountArray[card]++
        }

        var lineCount = 0
        var i = 3
        while (i < 15) {
            if (cardCountArray[i] == 3) {
                lineCount++
            } else {
                if (lineCount != 0) {
                    break
                }
            }
            i++
        }
        return if (lineCount * 3 == cards.size) {
            i - 1
        } else {
            -1
        }

    }
}
