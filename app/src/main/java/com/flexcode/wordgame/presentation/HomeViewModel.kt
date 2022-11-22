package com.flexcode.wordgame.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.flexcode.wordgame.data.MAX_NO_OF_WORDS
import com.flexcode.wordgame.data.SCORE_INCREASE
import com.flexcode.wordgame.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    var guessedWord by mutableStateOf("")
        private set

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    fun checkGuessedWord() {
        if (guessedWord.equals(currentWord, ignoreCase = true)){
            val newScore = _state.value.score.plus(SCORE_INCREASE)
            updateState(newScore)
        }else{
            _state.update {
                it.copy(isGuessedWordWrong = true)
            }
        }
        updateGuessedWord("")
    }

     fun updateGuessedWord(word:String){
        guessedWord = word
    }

    private fun updateState(newScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //GAME OVER DON'T PICK NEW WORD
            _state.update { state->
                state.copy(
                    score = newScore,
                    isGuessedWordWrong = false,
                    isGameOver = true,
                    currentWordCount = state.currentWordCount.inc(),
                )

            }
        }else{
            _state.update { state->
                state.copy(
                    score = newScore,
                    isGuessedWordWrong = false,
                    currentWordCount = state.currentWordCount.inc(),
                    currentShuffledWord = pickRandomWord()
                )
            }
        }
    }

    fun resetGame() {
        usedWords.clear()
        _state.value = UiState(currentShuffledWord = pickRandomWord())
    }

    fun skipWord() {
        updateState(_state.value.score)
        updateGuessedWord("")
    }

    private fun pickRandomWord(): String {
        currentWord = allWords.random()
        //check if the word has been used if yes shuffle until word not used is got
        return if (usedWords.contains(currentWord)){
            pickRandomWord()
        }else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(currentWord: String): String {
        val word = currentWord.toCharArray()
        word.shuffle()
        while (String(word) == currentWord){
            word.shuffle()
        }
        return String(word)
    }
}