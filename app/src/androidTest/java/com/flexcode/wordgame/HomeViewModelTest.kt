package com.flexcode.wordgame

import com.flexcode.wordgame.presentation.HomeViewModel
import junit.framework.TestCase.*
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

    companion object {
        private const val UPDATED_SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}