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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zttothers.frenchgrammar.presentation.viewmodel.LearningViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.SpellingQuizViewModel
import com.zttothers.frenchgrammar.ui.components.VerbCard
import com.zttothers.frenchgrammar.ui.theme.ColorCorrect
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchBlue
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchRed
import com.zttothers.frenchgrammar.ui.theme.ColorIncorrect
import com.zttothers.frenchgrammar.ui.theme.ColorPronounBg
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_primary
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_tertiary

// ─── Spelling Quiz Screen ─────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    navController: NavHostController,
    viewModel: SpellingQuizViewModel
) {
    val state = viewModel.state.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.currentIndex, state.submitted) {
        if (!state.submitted && !state.isFinished && !state.isLoading) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("拼寫練習", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!state.isLoading && state.questions.isNotEmpty()) {
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
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { Text("載入題目中…", color = Color.Gray) }
            }
            state.isFinished -> {
                QuizFinishScreen(
                    score = state.score,
                    total = state.questions.size,
                    onRestart = { viewModel.loadQuestions() },
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            else -> {
                val question = state.questions[state.currentIndex]
                val progress = (state.currentIndex + 1).toFloat() / state.questions.size

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

                    // Verb card
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
                                Text(
                                    text = question.verb.infinitive,
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = ColorFrenchBlue,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = question.verb.meaning,
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    fontStyle = FontStyle.Italic,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // Pronoun prompt
                    Text("請填入以下人稱的變位：", fontSize = 13.sp, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ColorPronounBg,
                        tonalElevation = 2.dp
                    ) {
                        Text(
                            text = question.pronounLabel,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorFrenchBlue,
                            modifier = Modifier.padding(horizontal = 28.dp, vertical = 10.dp)
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    // Text input
                    OutlinedTextField(
                        value = state.userInput,
                        onValueChange = { viewModel.onInputChange(it) },
                        placeholder = {
                            Text(
                                "輸入變位形式…",
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
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
                                            text = "正確答案：${question.correctAnswer}",
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
    }
}

// ─── Finish Screen ────────────────────────────────────────────────────────────

@Composable
fun QuizFinishScreen(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percentage = if (total > 0) score.toFloat() / total else 0f
    val (emoji, message) = when {
        percentage >= 0.9f -> "🏆" to "非常優秀！"
        percentage >= 0.7f -> "🎉" to "做得很好！"
        percentage >= 0.5f -> "📚" to "繼續加油！"
        else               -> "💪" to "再練習一次！"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emoji, fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text(message, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7FF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$score / $total",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ColorFrenchBlue
                )
                Text("答對題數", color = Color.Gray, fontSize = 14.sp)
                Spacer(Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { percentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = when {
                        percentage >= 0.7f -> ColorCorrect
                        percentage >= 0.4f -> md_theme_light_tertiary
                        else               -> ColorIncorrect
                    },
                    trackColor = Color.LightGray.copy(alpha = 0.4f)
                )
                Text(
                    "${(percentage * 100).toInt()}%",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorFrenchBlue)
        ) {
            Icon(Icons.Default.Refresh, null, modifier = Modifier.padding(end = 8.dp))
            Text("再練習一次", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(12.dp))
        FilledTonalButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("返回主頁", fontSize = 16.sp)
        }
    }
}

// ─── Card Learning Screen ─────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardLearningScreen(
    navController: NavHostController,
    viewModel: LearningViewModel
) {
    val learningState = viewModel.learningState.collectAsState().value

    if (learningState.verbs.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("單字卡學習") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) { Text("載入中…", color = Color.Gray) }
        }
        return
    }

    val currentVerb = learningState.verbs.getOrNull(learningState.currentVerbIndex)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("單字卡學習", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            val progress = (learningState.currentVerbIndex + 1).toFloat() / learningState.totalCount.toFloat()
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .padding(horizontal = 16.dp),
                color = md_theme_light_primary,
                trackColor = md_theme_light_primary.copy(alpha = 0.15f)
            )
            Text(
                "${learningState.currentVerbIndex + 1} / ${learningState.totalCount}",
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                color = Color.Gray
            )

            if (currentVerb != null) {
                VerbCard(
                    verb = currentVerb,
                    isFlipped = learningState.isFlipped,
                    onFlip = { viewModel.flipCard() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = { viewModel.previousCard() },
                        enabled = learningState.currentVerbIndex > 0,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                    Button(
                        onClick = { viewModel.markAsIncorrect() },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorIncorrect),
                        modifier = Modifier.weight(2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Close, null, modifier = Modifier.padding(end = 4.dp))
                        Text("不會")
                    }
                    Button(
                        onClick = { viewModel.markAsCorrect() },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorCorrect),
                        modifier = Modifier.weight(2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.padding(end = 4.dp))
                        Text("會了")
                    }
                    FilledTonalButton(
                        onClick = { viewModel.nextCard() },
                        enabled = learningState.currentVerbIndex < learningState.totalCount - 1,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
                    }
                }
            }
        }
    }
}
