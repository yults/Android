package com.example.calculator.expression;

import com.example.calculator.expression.exceptions.CountingException;
import com.example.calculator.expression.exceptions.OverflowException;

public class CheckedNegate extends AbstractUnaryAction {
    public CheckedNegate(CommonExpression right) {
        super(right, "-");
        this.hash += 4;
    }


    protected void overflow(int right) throws OverflowException {
        if (right == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
    @Override
    protected int doAction(int right) throws CountingException {
     overflow(right);
        return -right;
    }

    @Override
    protected double doAction(double a) {
        return 0;
    }

}



