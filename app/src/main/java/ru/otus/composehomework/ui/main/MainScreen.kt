package ru.otus.composehomework.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.saveable.rememberSaveable
import ru.otus.composehomework.R
import ru.otus.composehomework.ui.task1.Task1Screen
import ru.otus.composehomework.ui.task2.Task2Screen
import ru.otus.composehomework.ui.task3.Task3Screen

sealed class Task(val titleRes: Int, val screen: @Composable () -> Unit) {
    object Task1 : Task(R.string.task1_title, { Task1Screen() })
    object Task2 : Task(R.string.task2_title, { Task2Screen() })
    object Task3 : Task(R.string.task3_title, { Task3Screen() })
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedTaskId by rememberSaveable { mutableStateOf<Int?>(null) }
    val tasks = listOf(Task.Task1, Task.Task2, Task.Task3)
    val selectedTask = tasks.firstOrNull { it.titleRes == selectedTaskId }
    BackHandler(enabled = selectedTask != null) { selectedTaskId = null }

    val currentTask = selectedTask
    if (currentTask == null) {
        TaskListScreen(
            onTaskSelected = { task -> selectedTaskId = task.titleRes },
            modifier = modifier
        )
    } else {
        TaskDetailScreen(
            task = currentTask,
            onBack = { selectedTaskId = null },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(onTaskSelected: (Task) -> Unit, modifier: Modifier = Modifier) {
    val tasks = listOf(Task.Task1, Task.Task2, Task.Task3)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.titleRes }) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTaskSelected(task) }
                ) {
                    Text(
                        text = stringResource(task.titleRes),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(task: Task, onBack: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(task.titleRes),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            task.screen()
        }
    }
}
