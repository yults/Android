package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculator.expression.parser.Calculator.evaluate
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object {
        const val INPUT = "INPUT"
        const val RESULT = "RESULT"
        const val RESENTACT = "RESENTACT"
        const val ACT = "ACT"
    }

    var correct = true
    var resentAct = "="
    var act = true
    lateinit var input: TextView
    lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)
        result = findViewById(R.id.result)
        clear.setOnClickListener {
            input.text = ""
            act = true
            result.text = ""
        }
    }

    fun onNumber(view: View?) {
        if (view is Button) {
            input.text = buildString {
                append(input.text)
                append(view.text)
            }
            act = false
        }
    }

    fun onAct(view: View?) {
        if (view is Button && !act && input.length() > 0) {
            input.text = buildString {
                append(input.text)
                append(view.text)
            }
            resentAct = view.text.toString()
            act = true
        }
    }

    fun onRes(view: View?) {
        if (view is Button) {
            calc()
            resentAct = "="
            if (correct)
                input.text = result.text
            if (act) {
                input.text = ""
                act = true
            } else act = false
        }
    }


    fun calc() {
        result.text = try {
            correct = true
            evaluate(input.text.toString()).toString()
        } catch (e: Exception) {
            correct = false
            if (e.message != null)
                e.message.toString()
            else ""
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putCharSequence(INPUT, input.text)
        outState.putCharSequence(RESULT, result.text)
        outState.putString(RESENTACT, resentAct)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        input.text = savedInstanceState.getCharSequence(INPUT)
        result.text = savedInstanceState.getCharSequence(RESULT)
        act = savedInstanceState.getBoolean(ACT)
        resentAct = savedInstanceState.getString(RESENTACT).toString()
    }
}