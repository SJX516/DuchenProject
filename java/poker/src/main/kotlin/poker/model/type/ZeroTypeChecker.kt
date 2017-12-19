package poker.model.type

import java.util.Arrays

class ZeroTypeChecker : TypeChecker {

    override val type: CardGroupType
        get() = CardGroupType.ZERO

    override val myPossibleCardCount: List<Int>
        get() = Arrays.asList(0)

    override fun getMaxCardIfIsMyType(cards: MutableList<Int>): Int {
        return 0
    }

}
