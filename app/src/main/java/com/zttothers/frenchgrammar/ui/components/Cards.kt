package com.zttothers.frenchgrammar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zttothers.frenchgrammar.data.models.Verb
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchBlue
import com.zttothers.frenchgrammar.ui.theme.ColorFrenchRed
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_primary
import com.zttothers.frenchgrammar.ui.theme.md_theme_light_tertiary

@Composable
fun VerbCard(
    verb: Verb,
    isFlipped: Boolean = false,
    onFlip: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onFlip() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        if (!isFlipped) {
            // ── Front ─────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(ColorFrenchBlue, ColorFrenchBlue.copy(alpha = 0.75f))
                        )
                    )
                    .padding(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = verb.infinitive,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = verb.meaning,
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = "點擊查看變位",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.55f)
                    )
                }
            }
        } else {
            // ── Back ──────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = verb.infinitive,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorFrenchBlue
                )
                Text(
                    text = verb.meaning,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(Modifier.height(8.dp))
                ConjugationGrid(verb = verb)
            }
        }
    }
}

@Composable
fun ConjugationGrid(verb: Verb, modifier: Modifier = Modifier) {
    val conjugations = listOf(
        "Je" to verb.je,
        "Tu" to verb.tu,
        "Il / Elle" to verb.ilElle,
        "Nous" to verb.nous,
        "Vous" to verb.vous,
        "Ils / Elles" to verb.ilsElles
    )

    Column(modifier = modifier.fillMaxWidth()) {
        conjugations.forEachIndexed { index, (pronoun, form) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (index % 2 == 0) Color(0xFFF5F7FF) else Color.Transparent)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pronoun,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(0.35f),
                    color = ColorFrenchBlue.copy(alpha = 0.75f)
                )
                Text(
                    text = form.ifEmpty { "—" },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(0.65f),
                    color = Color(0xFF1A1A2E)
                )
            }
        }

        if (verb.pastParticiple.isNotEmpty()) {
            Spacer(Modifier.height(6.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "P.P.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(0.35f),
                    color = md_theme_light_tertiary
                )
                Text(
                    text = verb.pastParticiple,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.65f),
                    color = md_theme_light_primary
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    count: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Text(
                text = title,
                fontSize = 11.sp,
                color = color.copy(alpha = 0.75f),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}
