package com.duchen.template.usage.Poker

import com.duchen.template.usage.Poker.model.PutCardDetail
import poker.CardLibrary
import poker.model.CardGroup
import poker.model.Player

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

        fun printNewRound(newRound: Int) {
            if (newRound > 1) {
                println("||----------------------------------------------------\n")
            }
            println("||-------------------- ROUND $newRound -----------------------")
            println("||")
        }

        fun printPutCard(detail: PutCardDetail) {
            println(String.format("|| %-11s  %-10s   %s", detail.role, detail.putStrategy, detail.cardGroup))
        }

        fun win(player: Player) {
            println("\n\n---------------------- ${player.role} WIN ----------------------")
        }
    }
}

