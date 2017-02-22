package com.duchen.template.usage.Kotlin


interface Command<T> {
    fun execute() : T
}