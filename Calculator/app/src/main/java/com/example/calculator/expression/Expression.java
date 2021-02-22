package com.example.calculator.expression;

import com.example.calculator.expression.exceptions.CountingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression extends ToMiniString {
    int evaluate(int x) throws CountingException;
}
