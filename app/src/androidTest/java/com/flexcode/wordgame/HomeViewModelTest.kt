package com.flexcode.wordgame

import com.flexcode.wordgame.presentation.HomeViewModel
import junit.framework.TestCase.*
import org.junit.Assert.assertNotEquals
import org.junit.Test

class HomeViewModelTest {

    private val viewModel = HomeViewModel()


    @Test
    fun checkCorrectWordGuessedAndScoreIsUpdated(){
        var state = viewModel.state.value
        val correctWord = getUnshuffledWord(state.currentShuffledWord)

        viewModel.updateGuessedWord(correctWord)
        viewModel.checkGuessedWord()

        state = viewModel.state.value
        // Assert that checkGuessedWord() method updates isGuessedWordWrong is updated correctly.
        assertFalse(state.isGuessedWordWrong)
        // Assert that score is updated correctly
        assertEquals(UPDATED_SCORE_AFTER_FIRST_CORRECT_ANSWER,state.score)
    }

    @Test
    fun checkErrorIsSetIfIncorrectGuess() {
        val incorrectPlayerWord = "and"

        viewModel.updateGuessedWord(incorrectPlayerWord)
        viewModel.checkGuessedWord()

        val currentGameUiState = viewModel.state.value
        //Assert that score is unchanged
        assertEquals(0,currentGameUiState.score)
        //Assert that checkUserGuess() method updates isGuessedWordWrong correctly
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun checkFirstWordIsLoaded() {
        val state = viewModel.state.value

        val unShuffledWord = getUnshuffledWord(state.currentShuffledWord)

        assertNotEquals(unShuffledWord,state.currentShuffledWord)
//        assertTrue(state.currentWordCount == 1)
        assertTrue(state.score == 0)
        assertFalse(state.isGuessedWordWrong)
        assertFalse(state.isGameOver)
    }

    @Test
    fun checkUiStateIsUpdatedCorrectly(){
        var expectedScore = 0
        var currentUiState = viewModel.state.value
        var correctWord = getUnshuffledWord(currentUiState.currentShuffledWord)
        repeat(MAX_NO_OF_WORDS){
            expectedScore += SCORE_INCREASE
            viewModel.updateGuessedWord(correctWord)
            viewModel.checkGuessedWord()

            currentUiState = viewModel.state.value
            correctWord = getUnshuffledWord(currentUiState.currentShuffledWord)

            assertEquals(expectedScore,currentUiState.currentWordCount)
        }

        assertEquals(MAX_NO_OF_WORDS,currentUiState.currentWordCount)

        assertTrue(currentUiState.isGameOver)
    }

    companion object {
        private const val UPDATED_SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}