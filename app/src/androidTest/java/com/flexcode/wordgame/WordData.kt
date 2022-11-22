package com.flexcode.wordgame

const val MAX_NO_OF_WORDS = 20
const val SCORE_INCREASE = 10

val allWords: Set<String> =
    setOf(
        "at",
        "sea",
        "home",
        "arise",
        "banana",
        "android",
        "birthday",
        "briefcase",
        "motorcycle",
        "cauliflower"
    )

private val wordLengthMap: Map<Int,String> = allWords.associateBy({ it.length }, { it })

internal fun getUnshuffledWord(shuffledWord : String) = wordLengthMap[shuffledWord.length] ?: ""