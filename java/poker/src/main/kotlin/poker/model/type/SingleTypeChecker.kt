package poker.model.type

import java.util.Arrays

class SingleTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.SINGLE

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(1)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        return cards[0]
    }
}
