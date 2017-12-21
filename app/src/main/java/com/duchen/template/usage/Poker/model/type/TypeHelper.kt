package poker.model.type

import poker.model.CardGroup

import java.util.ArrayList

class TypeHelper private constructor() {

    internal var mCheckers: MutableList<TypeChecker> = ArrayList()

    init {
        mCheckers.add(ZeroTypeChecker())
        mCheckers.add(SingleTypeChecker())
        mCheckers.add(DoubleTypeChecker())
        mCheckers.add(ThreeTypeChecker())
        mCheckers.add(SingleLineTypeChecker())
        mCheckers.add(DoubleLineTypeChecker())
        mCheckers.add(ThreeLineTypeChecker())
        mCheckers.add(ThreeTakeOneTypeChecker())
        mCheckers.add(ThreeTakeTwoTypeChecker())
        mCheckers.add(ThreeTakeOneLineTypeChecker())
        mCheckers.add(ThreeTakeTwoLineTypeChecker())
        mCheckers.add(BombTypeChecker())
        mCheckers.add(FourTakeOneTypeChecker())
        mCheckers.add(FourTakeTwoTypeChecker())
        mCheckers.add(KingTypeChecker())
    }

    fun isOneCardGroup(cards: MutableList<Int>): CardGroup {
        val count = cards.size
        for (checker in mCheckers) {
            if (checker.myPossibleCardCount.contains(count)) {
                val maxCard = checker.getMaxCardIfIsMyType(cards)
                if (maxCard >= 0) {
                    val group = CardGroup()
                    group.cardList = cards.apply { sort() }
                    group.cardGroupType = checker.type
                    group.maxCard = maxCard
                    return group
                }
            }
        }
        val error = CardGroup()
        error.cardGroupType = CardGroupType.ERROR
        error.cardList = cards.apply { sort() }
        return error
    }

    companion object {
        var instance = TypeHelper()
    }
}
