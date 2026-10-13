package ru.otus.composehomework.ui.task3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.otus.composehomework.R

/**
 * Задание 3: Подключение ViewModel и комплексное состояние (MVI паттерн)
 * 
 * Развивайте Task2Screen, подключив ViewModel для управления состоянием.
 * 
 * MVI (Model-View-Intent) паттерн:
 * - State - единое состояние UI (Task3State) - определено в Task3Contracts.kt
 * - Intent - действия пользователя (Task3Intent) - определено в Task3Contracts.kt
 * - ViewModel обрабатывает Intent и обновляет State
 * 
 * Все контракты MVI (State, Intent, UiState) находятся в файле Task3Contracts.kt
 * 
 * Требования:
 * 1. Скопируйте код из Task2Screen.kt (или начните с Task1Screen, если нужно)
 * 2. Подключите ViewModel через: val viewModel: Task3ViewModel = viewModel()
 * 3. Используйте collectAsState() для наблюдения за единым состоянием:
 *    - val state by viewModel.state.collectAsState()
 *    - Доступ к полям: state.name, state.email, state.message, state.uiState, state.validationErrors
 * 4. Создайте форму с тремя TextField (имя, email, сообщение)
 * 5. Отправляйте Intent через viewModel.handleIntent():
 *    - Task3Intent.NameChanged(name) - при изменении имени
 *    - Task3Intent.EmailChanged(email) - при изменении email
 *    - Task3Intent.MessageChanged(message) - при изменении сообщения
 *    - Task3Intent.SubmitClicked - при нажатии "Отправить"
 *    - Task3Intent.RetryClicked - при нажатии "Повторить"
 *    - Task3Intent.ClearClicked - при нажатии "Очистить"
 * 6. Отображайте разные состояния UI:
 *    - UiState.Loading -> показать CircularProgressIndicator
 *    - UiState.Success -> показать результат
 *    - UiState.Error -> показать сообщение об ошибке
 *    - UiState.ValidationError -> показать ошибки валидации для каждого поля
 * 
 * Подсказки:
 * - Используйте TextField с параметрами value и onValueChange
 * - Для ошибок валидации используйте isError и supportingText
 * - Используйте Column для вертикальной компоновки формы
 * - Примените модификаторы: padding, spacing, fillMaxWidth
 * - Для индикатора загрузки используйте CircularProgressIndicator
 * - Отправляйте Intent: viewModel.handleIntent(Task3Intent.NameChanged(newName))
 */
@Composable
fun Task3Screen(
    modifier: Modifier = Modifier,
    viewModel: Task3ViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Task3Content(
        state = state,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}

@Composable
private fun Task3Content(
    state: Task3State,
    onIntent: (Task3Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val errors = state.validationErrors?.errors.orEmpty()
        OutlinedTextField(
            value = state.name,
            onValueChange = { onIntent(Task3Intent.NameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.name_hint)) },
            singleLine = true,
            isError = errors[FieldType.NAME] != null,
            supportingText = errors[FieldType.NAME]?.let { message ->
                { Text(message) }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = state.email,
            onValueChange = { onIntent(Task3Intent.EmailChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.email_hint)) },
            singleLine = true,
            isError = errors[FieldType.EMAIL] != null,
            supportingText = errors[FieldType.EMAIL]?.let { message ->
                { Text(message) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = state.message,
            onValueChange = { onIntent(Task3Intent.MessageChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.message_hint)) },
            minLines = 4,
            isError = errors[FieldType.MESSAGE] != null,
            supportingText = errors[FieldType.MESSAGE]?.let { message ->
                { Text(message) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onIntent(Task3Intent.SubmitClicked) },
                enabled = state.uiState !is UiState.Loading,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.submit))
            }
            Button(
                onClick = { onIntent(Task3Intent.ClearClicked) },
                enabled = state.uiState !is UiState.Loading,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.clear))
            }
        }

        when (val uiState = state.uiState) {
            UiState.Idle, is UiState.ValidationError -> Unit
            UiState.Loading -> Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            is UiState.Success -> Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(uiState.result.message, style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.result_id, uiState.result.id))
                }
            }
            is UiState.Error -> Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(uiState.message, color = MaterialTheme.colorScheme.onErrorContainer)
                    Button(onClick = { onIntent(Task3Intent.RetryClicked) }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
        }
    }
}
