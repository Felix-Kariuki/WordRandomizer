package com.flexcode.wordgame.presentation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flexcode.wordgame.data.MAX_NO_OF_WORDS
import com.flexcode.wordgame.ui.theme.WordGameTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {

    val state by homeViewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        GameStatus(
            wordCount = state.currentWordCount,
            score = state.score
        )
        GameLayout(
            currentShuffledWord = state.currentShuffledWord,
            isGuessedWordWrong = state.isGuessedWordWrong,
            guessedWord = homeViewModel.guessedWord,
            onGuessedWordChanged = { homeViewModel.updateGuessedWord(it) },
            onKeyboardDone = { homeViewModel.checkGuessedWord() }
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = { homeViewModel.skipWord() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "Skip")
            }
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp),
                onClick = { homeViewModel.checkGuessedWord() }) {
                Text(text = "Submit")
            }
        }
        if (state.isGameOver) {
            GameOverDialog(
                score = state.score,
                onPlayAgain = { homeViewModel.resetGame() },
                context = context
            )
        }
    }
}


@Composable
fun GameStatus(
    wordCount: Int,
    score: Int,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp)
    ) {
        Text(
            text = "$wordCount of $MAX_NO_OF_WORDS words",
            fontSize = fontSize
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = "Score : $score",
            fontSize = fontSize
        )
    }
}

@Composable
fun GameLayout(
    currentShuffledWord: String,
    isGuessedWordWrong: Boolean,
    guessedWord: String,
    onGuessedWordChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = currentShuffledWord,
            fontSize = 45.sp,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Unshuffle Word to create the correct word",
            fontSize = 17.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = guessedWord,
            singleLine = true,
            onValueChange = onGuessedWordChanged,
            modifier = Modifier.fillMaxSize(),
            label = {
                if (isGuessedWordWrong) {
                    Text(text = "Wrong Guess")
                } else {
                    Text(text = "Enter Your Word")
                }
            },
            isError = isGuessedWordWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            )
        )
    }

}

@Composable
fun GameOverDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context
) {
    val activity = context as Activity

    AlertDialog(
        onDismissRequest = {

        },
        title = { Text(text = "Hurraaaay!") },
        text = { Text(text = "You Scored: $score") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = {
                activity.finish()
            }) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Play Again")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WordGameTheme {
        HomeScreen()
    }
}