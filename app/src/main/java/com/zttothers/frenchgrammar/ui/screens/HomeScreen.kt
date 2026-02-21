package com.zttothers.frenchgrammar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zttothers.frenchgrammar.data.models.Verb
import com.zttothers.frenchgrammar.presentation.viewmodel.VerbViewModel
import com.zttothers.frenchgrammar.ui.components.StatCard
import com.zttothers.frenchgrammar.ui.navigation.Screen
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchBlue
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchRed
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_primary
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_secondary
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: VerbViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    val progressState = viewModel.progressState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🇫🇷", fontSize = 22.sp, modifier = Modifier.padding(end = 8.dp))
                        Text(
                            "法語文法練習",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // ── Header banner ──────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    ColorFrenchBlue,
                                    Color.White,
                                    ColorFrenchRed
                                )
                            )
                        )
                )
            }

            // ── Search ─────────────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.search(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    label = { Text("搜尋不規則動詞…") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.search("") }) {
                                Icon(Icons.Default.Close, contentDescription = null,
                                    modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )
            }

            // ── Search results ─────────────────────────────────────────
            if (uiState.searchQuery.isNotEmpty()) {
                if (uiState.verbs.isEmpty()) {
                    item {
                        Text(
                            "無搜尋結果",
                            color = androidx.compose.ui.graphics.Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                } else {
                    item {
                        Text(
                            "搜尋結果：${uiState.verbs.size} 筆",
                            fontSize = 13.sp,
                            color = androidx.compose.ui.graphics.Color.Gray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
                        )
                    }
                    items(uiState.verbs) { verb ->
                        SearchResultCard(
                            verb = verb,
                            query = uiState.searchQuery,
                            onClick = { navController.navigate(Screen.CardLearning.withVerb(verb.id)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
                return@LazyColumn
            }

            // ── Progress stats ────────────────────────────────────────────
            item {
                val total = progressState.newCount + progressState.learningCount + progressState.masteredCount
                val masteredPct = if (total > 0) progressState.masteredCount.toFloat() / total else 0f

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        "學習進度",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatCard(
                            title = "未學",
                            count = progressState.newCount,
                            color = md_theme_light_secondary,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "學習中",
                            count = progressState.learningCount,
                            color = md_theme_light_primary,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "已掌握",
                            count = progressState.masteredCount,
                            color = md_theme_light_tertiary,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { masteredPct },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = md_theme_light_tertiary,
                        trackColor = Color.LightGray.copy(alpha = 0.35f)
                    )
                    Text(
                        "已掌握 ${(masteredPct * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }
            }

            // ── Mode cards ────────────────────────────────────────────────
            item {
                Text(
                    "學習模式",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
            }

            item {
                ModeCard(
                    emoji = "✏️",
                    title = "拼寫練習",
                    subtitle = "輸入某個人稱的不規則動詞變位形式",
                    gradientColors = listOf(ColorFrenchBlue.copy(alpha = 0.85f), ColorFrenchBlue.copy(alpha = 0.6f)),
                    onClick = { navController.navigate(Screen.Quiz.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item {
                ModeCard(
                    emoji = "📇",
                    title = "單字卡學習",
                    subtitle = "翻轉卡片查看不規則動詞所有人稱變位",
                    gradientColors = listOf(md_theme_light_primary.copy(alpha = 0.85f), md_theme_light_primary.copy(alpha = 0.6f)),
                    onClick = { navController.navigate(Screen.CardLearning.plainRoute) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item {
                ModeCard(
                    emoji = "🔢",
                    title = "數字練習",
                    subtitle = "1-100，數字與法語雙向練習",
                    gradientColors = listOf(ColorFrenchRed.copy(alpha = 0.80f), ColorFrenchRed.copy(alpha = 0.55f)),
                    onClick = { navController.navigate(Screen.Numbers.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item {
                ModeCard(
                    emoji = "📊",
                    title = "學習統計",
                    subtitle = "查看詳細的掌握情況",
                    gradientColors = listOf(md_theme_light_tertiary.copy(alpha = 0.85f), md_theme_light_tertiary.copy(alpha = 0.6f)),
                    onClick = { navController.navigate(Screen.Progress.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun ModeCard(
    emoji: String,
    title: String,
    subtitle: String,
    gradientColors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradientColors))
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 32.sp, modifier = Modifier.padding(end = 16.dp))
                Column {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                    Text(
                        subtitle,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(
    verb: Verb,
    query: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val matchedForms = buildList {
        val q = query.lowercase()
        mapOf(
            "je " to verb.je, "tu " to verb.tu, "il/elle " to verb.ilElle,
            "nous " to verb.nous, "vous " to verb.vous, "ils/elles " to verb.ilsElles,
            "p.p. " to verb.pastParticiple
        ).forEach { (label, form) ->
            if (form.contains(q, ignoreCase = true) &&
                !verb.infinitive.contains(q, ignoreCase = true)) {
                add("$label$form")
            }
        }
    }
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    verb.infinitive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    verb.meaning,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (matchedForms.isNotEmpty()) {
                    Text(
                        matchedForms.take(2).joinToString("  "),
                        fontSize = 12.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavHostController,
    viewModel: VerbViewModel
) {
    val progressState = viewModel.progressState.collectAsState().value
    val total = progressState.newCount + progressState.learningCount + progressState.masteredCount
    val masteredPct = if (total > 0) progressState.masteredCount.toFloat() / total else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("學習統計", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(title = "未學", count = progressState.newCount, color = md_theme_light_secondary, modifier = Modifier.weight(1f))
                StatCard(title = "學習中", count = progressState.learningCount, color = md_theme_light_primary, modifier = Modifier.weight(1f))
                StatCard(title = "已掌握", count = progressState.masteredCount, color = md_theme_light_tertiary, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { masteredPct },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = md_theme_light_tertiary,
                trackColor = Color.LightGray.copy(alpha = 0.35f)
            )
            Text(
                "已掌握 ${(masteredPct * 100).toInt()}%  ·  共 $total 個動詞",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}