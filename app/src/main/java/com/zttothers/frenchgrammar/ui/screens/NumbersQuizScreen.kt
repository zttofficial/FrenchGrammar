package com.zttothers.frenchgrammar.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zttothers.frenchgrammar.presentation.viewmodel.NumberQuizDirection
import com.zttothers.frenchgrammar.presentation.viewmodel.NumbersQuizViewModel
import com.zttothers.frenchgrammar.ui.theme.ColorCorrect
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchBlue
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchRed
import com.zttothers.frenchgrammar.ui.theme.ColorIncorrect
import com.zttothers.frenchgrammar.ui.theme.ColorPronounBg
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_primary
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_tertiary
import com.zttothers.frenchgrammar.ui.utils.SoundFeedback
import com.zttothers.frenchgrammar.ui.utils.rememberTts
import com.zttothers.frenchgrammar.ui.utils.speakFrench

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumbersQuizScreen(
    navController: NavHostController,
    viewModel: NumbersQuizViewModel
) {
    val state = viewModel.state.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    val tts by rememberTts(context)

    LaunchedEffect(state.currentIndex, state.submitted) {
        if (!state.submitted && !state.isFinished) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(state.submitted) {
        if (state.submitted) {
            if (state.isCorrect) SoundFeedback.playCorrect()
            else SoundFeedback.playIncorrect()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("數字練習", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state.questions.isNotEmpty() && !state.isFinished) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = md_theme_light_primary.copy(alpha = 0.12f),
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Text(
                                text = "✓ ${state.score}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                color = md_theme_light_primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        if (state.isFinished) {
            QuizFinishScreen(
                score = state.score,
                total = state.questions.size,
                onRestart = { viewModel.loadQuestions() },
                onBack = { navController.popBackStack() },
                modifier = Modifier.padding(paddingValues)
            )
            return@Scaffold
        }

        if (state.questions.isEmpty()) return@Scaffold

        val question = state.questions[state.currentIndex]
        val progress = (state.currentIndex + 1).toFloat() / state.questions.size
        val isDigitToFrench = question.direction == NumberQuizDirection.DIGIT_TO_FRENCH
        val isListening = question.direction == NumberQuizDirection.LISTENING

        // Auto-play TTS when a LISTENING question appears
        LaunchedEffect(state.currentIndex) {
            if (isListening) tts?.speakFrench(question.prompt)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = md_theme_light_primary,
                trackColor = md_theme_light_primary.copy(alpha = 0.15f)
            )
            Text(
                "${state.currentIndex + 1} / ${state.questions.size}",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Prompt card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7FF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    ColorFrenchBlue.copy(alpha = 0.08f),
                                    ColorFrenchRed.copy(alpha = 0.06f)
                                )
                            )
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Direction badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = when {
                                isListening   -> Color(0xFFFFF3E0)
                                isDigitToFrench -> ColorFrenchBlue.copy(alpha = 0.12f)
                                else            -> md_theme_light_tertiary.copy(alpha = 0.18f)
                            }
                        ) {
                            Text(
                                text = when {
                                    isListening     -> "🔊 聽力題"
                                    isDigitToFrench -> "數字 → 法語"
                                    else            -> "法語 → 數字"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = when {
                                    isListening     -> Color(0xFFE65100)
                                    isDigitToFrench -> ColorFrenchBlue
                                    else            -> md_theme_light_tertiary
                                },
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(Modifier.height(16.dp))

                        if (isListening) {
                            // LISTENING: show only a big play button, no text
                            IconButton(
                                onClick = { tts?.speakFrench(question.prompt) },
                                modifier = Modifier.size(72.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "播放",
                                    tint = if (tts != null) Color(0xFFE65100) else Color.LightGray,
                                    modifier = Modifier.size(52.dp)
                                )
                            }
                            Text(
                                text = "點擊播放音訊",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        } else {
                            Text(
                                text = question.prompt,
                                fontSize = if (isDigitToFrench) 56.sp else 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = ColorFrenchBlue,
                                textAlign = TextAlign.Center,
                                letterSpacing = if (isDigitToFrench) 2.sp else 0.5.sp
                            )
                            // TTS speaker button
                            Spacer(Modifier.height(8.dp))
                            IconButton(onClick = { tts?.speakFrench(question.prompt) }) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "朗讀",
                                    tint = if (tts != null) ColorFrenchBlue.copy(alpha = 0.65f)
                                           else Color.LightGray
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Hint label
            Text(
                text = when {
                    isListening     -> "輸入阿拉伯數字或法語拼寫均可："
                    isDigitToFrench -> "用法語拼寫這個數字："
                    else            -> "這個法語數字是幾？"
                },
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))

            // Input field
            OutlinedTextField(
                value = state.userInput,
                onValueChange = { viewModel.onInputChange(it) },
                placeholder = {
                    Text(
                        if (isDigitToFrench) "輸入法語…" else "輸入數字…",
                        color = Color.Gray.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                enabled = !state.submitted,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (state.submitted) {
                        if (state.isCorrect) ColorCorrect else ColorIncorrect
                    } else ColorFrenchBlue,
                    unfocusedBorderColor = if (state.submitted) {
                        if (state.isCorrect) ColorCorrect else ColorIncorrect
                    } else Color.LightGray,
                    disabledBorderColor = if (state.isCorrect) ColorCorrect else ColorIncorrect,
                    disabledTextColor = if (state.isCorrect) ColorCorrect else ColorIncorrect
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = if (!isDigitToFrench && !isListening) KeyboardType.Number else KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (!state.submitted) {
                        viewModel.submit(); keyboardController?.hide()
                    } else {
                        viewModel.next()
                    }
                })
            )

            Spacer(Modifier.height(16.dp))

            // Result feedback
            AnimatedVisibility(
                visible = state.submitted,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut()
            ) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.isCorrect)
                            ColorCorrect.copy(alpha = 0.10f)
                        else
                            ColorIncorrect.copy(alpha = 0.10f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (state.isCorrect) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (state.isCorrect) ColorCorrect else ColorIncorrect,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (state.isCorrect) "正確！" else "答錯了",
                                fontWeight = FontWeight.Bold,
                                color = if (state.isCorrect) ColorCorrect else ColorIncorrect,
                                fontSize = 15.sp
                            )
                            if (!state.isCorrect) {
                                Text(
                                    text = "正確答案：${question.answer}",
                                    color = Color.DarkGray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // Action button
            Button(
                onClick = {
                    if (!state.submitted) {
                        viewModel.submit(); keyboardController?.hide()
                    } else {
                        viewModel.next()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.submitted && state.isCorrect)
                        ColorCorrect else ColorFrenchBlue
                ),
                enabled = state.submitted || state.userInput.isNotBlank()
            ) {
                if (state.submitted) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null,
                        modifier = Modifier.padding(end = 6.dp))
                    Text(
                        if (state.currentIndex + 1 < state.questions.size) "下一題" else "查看結果",
                        fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                    )
                } else {
                    Icon(Icons.Default.Check, null, modifier = Modifier.padding(end = 6.dp))
                    Text("確認", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
