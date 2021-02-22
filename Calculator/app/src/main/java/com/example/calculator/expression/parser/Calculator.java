package com.example.calculator.expression.parser;

import com.example.calculator.expression.exceptions.ParsingException;

public class Calculator {
    public static int evaluate(String s) throws Exception {
        ExpressionParser parser = new ExpressionParser();
        return parser.parse(s).evaluate(0);

    }
}
