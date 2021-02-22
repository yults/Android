package com.example.calculator


import com.example.calculator.expression.exceptions.DivisionByZeroException
import com.example.calculator.expression.parser.Calculator.evaluate
import org.junit.Assert.assertEquals
import org.junit.Test


class EvaluateUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(3, evaluate("1 + 2"))
    }

    @Test
    fun subtract_isCorrect() {
        assertEquals(4, evaluate("6 - 2"))
    }

    @Test
    fun multiply_isCorrect() {
        assertEquals(56, evaluate("7 * 8"))
    }

    @Test
    fun divide_isCorrect() {
        assertEquals(5, evaluate("15 / 3"))
    }

    @Test
    fun all_isCorrect() {
        assertEquals(28, evaluate("12 - 8 / 4  + 3 * 6"))
    }

    @Test(expected = DivisionByZeroException::class)
    fun divisionByZero_isCorrect() {
        evaluate("2 / 0")
    }

}