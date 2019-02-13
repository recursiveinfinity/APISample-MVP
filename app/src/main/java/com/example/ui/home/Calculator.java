package com.example.ui.home;

import java.io.IOException;
import java.util.IllegalFormatException;

public class Calculator {

    private float result;

    private Battery battery;

    public Calculator(Battery battery) {
        this.battery = battery;
    }

    @Deprecated
    public float add(float a, float b) {
        result =  a + b;
        return result;
    }

    public float addWithRounding(float a, float b) {
        result =  a + b;
        result = Math.round(result);
        return result;
    }

    public boolean areTheNumbersTheSame(int a, int b) {
        return a == b;
    }


    public int divide(int a, int b) throws Exception {
        if (battery.getBatteryLevel() < 10) {
            throw new Exception("Change the battery");

        }
        if (b != 0) {
            return a / b;
        }
       throw new Exception("Please don't use zero as the second argument");
    }

}
