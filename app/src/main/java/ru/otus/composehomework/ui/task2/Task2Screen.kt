package ru.otus.composehomework.ui.task2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.otus.composehomework.R

/**
 * Задание 2: Добавление состояния
 * 
 * Развивайте Task1Screen, добавив счетчик кликов.
 * 
 * Требования:
 * 1. Скопируйте код из Task1Screen.kt
 * 2. Добавьте счетчик используя: var count by remember { mutableStateOf(0) }
 * 3. Кнопка должна увеличивать счетчик при каждом нажатии
 * 4. Отобразите текущее значение счетчика в Text
 * 
 * Подсказки:
 * - Используйте делегат by для удобной работы с состоянием
 * - Не забудьте импортировать: androidx.compose.runtime.getValue и setValue
 * - Обновите текст кнопки или добавьте отдельный Text для счетчика
 */
@Composable
fun Task2Screen(modifier: Modifier = Modifier) {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top)
    ) {
        Text(
            text = stringResource(R.string.welcome_text),
            style = MaterialTheme.typography.headlineSmall
        )
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(R.string.welcome_text),
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.counter, count),
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick = { count++ }) {
            Text(text = stringResource(R.string.click_me))
        }
    }
}
