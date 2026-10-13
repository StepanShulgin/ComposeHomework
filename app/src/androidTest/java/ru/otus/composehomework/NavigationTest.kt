package ru.otus.composehomework

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.pressBack
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun selectedTaskSurvivesRecreationAndSystemBackReturnsToList() {
        compose.onNodeWithText("Задание 2: Добавление состояния").performClick()
        compose.onNodeWithText("Нажми меня").performClick()
        compose.onNodeWithText("Счетчик: 1").assertIsDisplayed()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Нажми меня").assertIsDisplayed()
        pressBack()
        compose.onNodeWithText("Задание 1: Переписывание XML на Compose").assertIsDisplayed()
    }
}
