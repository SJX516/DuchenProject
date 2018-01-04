package com.duchen.template.usage.Poker

import poker.CardLibrary
import poker.model.CardGroup

class PrintUtil {

    companion object {

        fun printCardGroups(all : List<Int>, groups: List<CardGroup>) {
            val sb = StringBuilder()
            sb.append("||----------------------------------------------------\n")
            sb.append("||  ORIGIN_CARD").append(" ")
            for (card in all) {
                sb.append(CardLibrary.Companion.getCardChar(card))
            }
            sb.append("\n")
            for (group in groups) {
                sb.append("||  " + group + "\n")
            }
            sb.append("||----------------------------------------------------\n")
            println(sb.toString())
        }
    }
}

