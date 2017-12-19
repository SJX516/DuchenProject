package poker.model.type

import java.util.Arrays

class DoubleLineTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.DOUBLE_LINE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(6, 8, 10, 12, 14, 16, 18, 20)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        val cardCountArray = IntArray(18)
        for (card in cards) {
            cardCountArray[card]++
        }

        var lineCount = 0
        var i = 3
        while (i < 15) {
            if (cardCountArray[i] == 2) {
                lineCount++
            } else {
                if (lineCount != 0) {
                    break
                }
            }
            i++
        }
        return if (lineCount * 2 == cards.size) {
            i - 1
        } else {
            -1
        }
    }
}
