package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE  = "com.example.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.showAnswerButton.setOnClickListener {
            quizViewModel.answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            quizViewModel.answerIsShown = true

            saveView(quizViewModel.answerIsShown)
        }

        saveView(quizViewModel.answerIsShown)
    }

    private fun saveView(isAnswerShown: Boolean) {
        if (isAnswerShown) {
            quizViewModel.currentQuestionIsCheated = true

            binding.answerTextView.setText(quizViewModel.answerText)

            setAnswerShownResult(quizViewModel.currentQuestionIsCheated)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }

        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
