package com.duchen.template.usage.Poker

import poker.CardLibrary
import poker.model.CardGroup

class PrintUtil {

    companion object {

        fun getListStr(all : List<Int>) : String{
            return getListStr(null, all)
        }

        fun getListStr(sb : StringBuilder?, all : List<Int>) : String {
            var sb2 = sb
            if (sb2 == null) {
                sb2 = StringBuilder()
            }
            for (card in all) {
                sb2.append(CardLibrary.Companion.getCardChar(card))
            }
            return sb2.toString()
        }

        fun printCardGroups(all : List<Int>, groups: List<CardGroup>) {
            val sb = StringBuilder()
            sb.append("||----------------------------------------------------\n")
            sb.append("||  ORIGIN_CARD").append(" ")
            getListStr(sb, all)
            sb.append("\n")
            for (group in groups) {
                sb.append("||  " + group + "\n")
            }
            sb.append("||----------------------------------------------------\n")
            println(sb.toString())
        }
    }
}

