package ru.otus.composehomework.ui.task1

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
 * Задание 1: Переписывание XML на Compose
 * 
 * Перепишите экран из Task1Fragment (XML layout) на Compose.
 * 
 * Требования:
 * 1. Создайте функцию @Composable Task1Screen()
 * 2. Используйте компоненты: Text, Button, Image
 * 3. Используйте Column для вертикальной компоновки
 * 4. Примените модификаторы
 * 5. Реализуйте ту же функциональность: при нажатии кнопки изменяйте текст
 * 
 * Подсказки:
 * - Используйте remember { mutableStateOf() } для хранения состояния текста
 * - Для изображения используйте Image с painterResource или Icon
 * - Примените модификатор .padding(16.dp) к Column
 * - Используйте Arrangement.spacedBy() для отступов между элементами
 */
@Composable
fun Task1Screen(modifier: Modifier = Modifier) {
    val initialText = stringResource(R.string.welcome_text)
    var welcomeText by remember(initialText) { mutableStateOf(initialText) }
    val clickedText = stringResource(R.string.button_clicked)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top)
    ) {
        Text(
            text = welcomeText,
            style = MaterialTheme.typography.headlineSmall
        )
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(R.string.welcome_text),
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Button(onClick = { welcomeText = clickedText }) {
            Text(text = stringResource(R.string.click_me))
        }
    }
}
