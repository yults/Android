package com.example.calculator.expression.parser;

import com.example.calculator.expression.exceptions.BracketExpectedException;

public interface ExpressionSource {
    boolean hasNext();

    char nextChar() throws BracketExpectedException;
}
