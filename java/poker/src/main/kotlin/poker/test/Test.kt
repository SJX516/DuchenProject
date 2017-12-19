package poker.test

import poker.Poker
import poker.logic.HandCardLogic

import java.util.ArrayList
import java.util.Arrays

fun main(args: Array<String>) {
    println()
    println()
    println("--------------------------------test start--------------------------------")
    println()
    testPoker()
}

fun testPoker() {
    val poker = Poker()
    poker.startNewGame()
}

fun testIsOneCardGroup() {
    val logic = HandCardLogic()
    println(logic.getOneCardGroup(Arrays.asList(10)))
    println(logic.getOneCardGroup(Arrays.asList(14, 14)))
    println(logic.getOneCardGroup(Arrays.asList(12, 12, 12)))
    println(logic.getOneCardGroup(Arrays.asList(3, 4, 5, 6, 7)))
    println(logic.getOneCardGroup(Arrays.asList(12, 10, 12, 10, 11, 11)))
    println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 4, 6, 6, 6, 5, 5, 5)))
    println(logic.getOneCardGroup(Arrays.asList(11, 11, 3, 11)))
    println(logic.getOneCardGroup(Arrays.asList(8, 3, 3, 8, 8)))
    println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 11, 4, 4, 11, 4, 5, 6, 5, 8, 5, 6, 8)))
    println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 9, 4, 5, 6, 5, 8, 5)))
    println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 3)))
    println(logic.getOneCardGroup(Arrays.asList(3, 3, 3, 5, 3)))
    println(logic.getOneCardGroup(Arrays.asList(3, 3, 4, 3, 4, 3)))
    println(logic.getOneCardGroup(Arrays.asList(16, 17)))
    println(logic.getOneCardGroup(ArrayList()))
    println(logic.getOneCardGroup(Arrays.asList(12, 12, 11, 11)))
}
