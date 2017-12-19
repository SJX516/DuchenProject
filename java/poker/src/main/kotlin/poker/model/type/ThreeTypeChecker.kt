package poker.model.type

import java.util.Arrays

class ThreeTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.THREE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(3)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        return if (cards[0] == cards[1] && cards[1] == cards[2]) {
            cards[0]
        } else -1
    }
}
