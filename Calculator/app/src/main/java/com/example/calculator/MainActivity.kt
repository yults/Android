package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    companion object {
        const val INPUT = "INPUT"
        const val RESULT = "RESULT"
        const val  RESENTACT = "RESENTACT"
        const val EXPR = "EXPRESSION"
        const val ACT = "ACT"
    }
    private var expr : Double = 0.0
    var resentAct = "="
    var act = true
    lateinit var input : TextView
    lateinit var result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)
        result = findViewById(R.id.result)
        clear.setOnClickListener {
            input.text = ""
            act = true
            result.text = "0"
            expr = 0.0
        }
    }
    fun onNumber(view : View?) {
        if (view is Button) {
            val resultText = result.text?.toString()
            input.text = buildString {
                append(input.text)
                append(view.text)
            }
            if (resultText?.compareTo("0") == 0) {
                result.text = view.text.toString()
            }
            else {
                result.text = buildString {
                    append(result.text)
                    append(view.text)
                }
            }
            act = false
        }
    }
    fun onAct(view : View?) {
        if (view is Button && !act && input.length() > 0) {
            if (resentAct != "=") calc();
            val resultText = result.text.toString()
            if (resentAct != "=") input.text = resultText
            input.text = buildString {
                append(input.text)
                append(view.text)
            }
            expr = resultText.toDouble()
            resentAct = view.text.toString()
            act = true
            result.text = "0"
        }
    }

    fun onRes(view : View?) {
        if (view is Button) {
            calc()
            resentAct = "="
            input.text = result.text
            if (act) {
                input.text = ""
                expr = 0.0
                act = true
            }
            else act = false
        }
    }

    fun calc() {
        val resultText = result.text.toString()
        val exprDouble = resultText.toDouble()
        if (resentAct.compareTo("+") == 0) {
            expr += exprDouble
        }
        if (resentAct.compareTo("-") == 0) {
            expr -= exprDouble
        }
        if (resentAct.compareTo("*") == 0) {
            expr *= exprDouble
        }
        if (resentAct.compareTo("/") == 0) {
            expr /= exprDouble
        }
        if (resentAct.compareTo("=") == 0) {
            expr = exprDouble
        }
        val exprInt = expr.toInt()
        if (expr - exprInt == 0.0) {
            result.text = exprInt.toString()
        } else {
            result.text = expr.toString()
        }
    }
    fun onPoint(view : View?) {
        if (input.length() == 0 || act) {
            input.text = buildString {
                append(input.text)
                append("0.")
            }
            result.text = "0."
        }
        else if (input.text.substring(input.length() - 1, input.length()) != ".") {
            input.text = buildString {
                append(input.text?.toString())
                append(".")
            }
            result.text = buildString {
                append(result.text?.toString())
                append(".")
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putDouble(EXPR, expr)
        outState.putCharSequence(INPUT, input.text)
        outState.putCharSequence(RESULT, result.text)
        outState.putString(RESENTACT, resentAct)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        input.text = savedInstanceState.getCharSequence(INPUT)
        result.text = savedInstanceState.getCharSequence(RESULT)
        expr = savedInstanceState.getDouble(EXPR)
        act = savedInstanceState.getBoolean(ACT)
        resentAct = savedInstanceState.getString(RESENTACT).toString()
    }
}