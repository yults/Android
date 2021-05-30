package com.example.calculator.expression.parser;

import com.example.calculator.expression.TripleExpression;
import com.example.calculator.expression.exceptions.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws UnknownSymbolException, BracketExpectedException, OverflowException, UnaryExpectedException;
}
