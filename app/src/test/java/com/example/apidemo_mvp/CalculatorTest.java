package com.example.apidemo_mvp;

import com.example.ui.home.Battery;
import com.example.ui.home.Calculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import retrofit2.http.PUT;

public class CalculatorTest {
    private Calculator calculator;

    @Mock
    private Battery battery;

    @Before
    public void setup() {
        calculator = new Calculator(battery);
    }

    @Test
    public void testAdd() {
        Assert.assertEquals(13.2, calculator.add(7.0f, 6.2f), 0.01);
        Assert.assertNotEquals(15.0, calculator.add(7.0f, 7.9f));
    }

    @Test
    public void testAddWithRounding() {
        Assert.assertEquals(13.0,
                calculator.addWithRounding(7.0f, 6.2f), 0.02);

    }

    @Test
    public void testAreTheNumbersTheSame() {
        Assert.assertTrue(calculator.areTheNumbersTheSame(5, 5));
        Assert.assertFalse(calculator.areTheNumbersTheSame(5, 6));
    }

    @Test(expected = Exception.class)
    public void testDivide() throws Exception {

        Assert.assertEquals(2, calculator.divide(10, 5));
        Assert.assertEquals(-1, calculator.divide(5, 0));
    }

    @Test(expected = Exception.class)
    public void testDivide_LowBattery() throws Exception {
        Mockito.when(battery.getBatteryLevel())
                .thenReturn(6);
        calculator.divide(0, 5);
    }
}
