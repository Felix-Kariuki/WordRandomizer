package com.flexcode.wordgame.presentation

data class UiState(
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val currentWordCount: Int = 0,
    val currentShuffledWord: String = ""
)
