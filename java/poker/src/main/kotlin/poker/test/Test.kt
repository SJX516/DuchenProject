package com.duchen.poker.test

import com.duchen.poker.Poker
import com.duchen.poker.logic.HandCardLogic

import java.util.ArrayList
import java.util.Arrays

fun main(args: Array<String>) {
    println()
    println("--------------------------------test start--------------------------------")
    testPoker()
}

fun testPoker() {
    val poker = Poker()
    poker.startNewGame()
}

fun testIsOneCardGroup() {
    val logic = HandCardLogic()
    println(logic.isOneCardGroup(Arrays.asList(10)))
    println(logic.isOneCardGroup(Arrays.asList(14, 14)))
    println(logic.isOneCardGroup(Arrays.asList(12, 12, 12)))
    println(logic.isOneCardGroup(Arrays.asList(3, 4, 5, 6, 7)))
    println(logic.isOneCardGroup(Arrays.asList(12, 10, 12, 10, 11, 11)))
    println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 4, 6, 6, 6, 5, 5, 5)))
    println(logic.isOneCardGroup(Arrays.asList(11, 11, 3, 11)))
    println(logic.isOneCardGroup(Arrays.asList(8, 3, 3, 8, 8)))
    println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 11, 4, 4, 11, 4, 5, 6, 5, 8, 5, 6, 8)))
    println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 4, 4, 9, 4, 5, 6, 5, 8, 5)))
    println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 3)))
    println(logic.isOneCardGroup(Arrays.asList(3, 3, 3, 5, 3)))
    println(logic.isOneCardGroup(Arrays.asList(3, 3, 4, 3, 4, 3)))
    println(logic.isOneCardGroup(Arrays.asList(16, 17)))
    println(logic.isOneCardGroup(ArrayList()))
    println(logic.isOneCardGroup(Arrays.asList(12, 12, 11, 11)))
}
