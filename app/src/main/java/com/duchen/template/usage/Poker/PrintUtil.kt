package com.duchen.template.usage.Poker

import poker.model.CardGroup

class PrintUtil {

    companion object {

        fun printCardGroups(groups: List<CardGroup>) {
            val sb = StringBuilder()
            sb.append("||----------------------------------------------------\n")
            for (group in groups) {
                sb.append("||  " + group + "\n")
            }
            sb.append("||----------------------------------------------------\n")
            println(sb.toString())
        }
    }
}

