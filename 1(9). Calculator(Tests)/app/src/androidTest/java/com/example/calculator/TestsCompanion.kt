package com.example.calculator

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

class EspressoFunctions {
    companion object{
        fun clickOnView(id: Int){
            Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.click())
        }
        fun match(id:Int, expectedValue :String){
            Espresso.onView(ViewMatchers.withId(id))
                .check(ViewAssertions.matches(ViewMatchers.withText(expectedValue)))
        }
    }
}