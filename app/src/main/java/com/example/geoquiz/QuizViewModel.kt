package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val ANSWER_TEXT_KEY = "ANSWER_TEXT_KEY"
const val ANSWER_IS_SHOWN = "ANSWER_IS_SHOWN"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val maximumTokens = 3

    private var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var answerIsShown: Boolean
        get() = savedStateHandle[ANSWER_IS_SHOWN] ?: false
        set(value) = savedStateHandle.set(ANSWER_IS_SHOWN, value)

    var answerText: Int
        get() = savedStateHandle[ANSWER_TEXT_KEY] ?: 0
        set(value) = savedStateHandle.set(ANSWER_TEXT_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    var currentQuestionIsCheated: Boolean
        get() = questionBank[currentIndex].isCheated
        set(value) {
            questionBank[currentIndex].isCheated = value
        }

    var cheatTokens: Int
        get() = savedStateHandle[ANSWER_IS_SHOWN] ?: maximumTokens
        set(value) {
            if (value == cheatTokens - 1)
                savedStateHandle[ANSWER_IS_SHOWN] = value
        }
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveBack() {
        currentIndex = if (currentIndex > 0) (currentIndex - 1) % questionBank.size else currentIndex
    }
}
