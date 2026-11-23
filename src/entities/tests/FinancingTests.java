package entities.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import entities.Financing;
import entities.tests.factory.FinancingFactory;

public class FinancingTests {

    private static final double DELTA = 0.000001;
    
    @Test
    public void constructorShouldCreateObjectWhenDataIsValid() {
        double totalAmount = 100_000.0;
        double income = 2_000.0;
        int months = 100;

        Financing financing = new Financing(totalAmount, income, months);

        Assertions.assertEquals(totalAmount, financing.getTotalAmount(), DELTA);
        Assertions.assertEquals(income, financing.getIncome(), DELTA);
        Assertions.assertEquals(months, financing.getMonths());
    }
    
    
    @Test
    public void constructorShouldThrowExceptionWhenDataIsInvalid() {
        double totalAmount = 100_000.0;
        double income = 2_000.0;
        int months = 20; // Prestação de 4.000, acima do limite de 1.000

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Financing(totalAmount, income, months);
        });
    }
    
    
    @Test
    public void setTotalAmountShouldUpdateValueWhenDataIsValid() {
        Financing financing = FinancingFactory.createValidFinancing();
        double expectedValue = 110_000.0;

        financing.setTotalAmount(expectedValue);

        Assertions.assertEquals(expectedValue, financing.getTotalAmount(), DELTA);
    }
    
    
    @Test
    public void setTotalAmountShouldThrowExceptionWhenDataIsInvalid() {
        Financing financing = FinancingFactory.createValidFinancing();
        double invalidTotalAmount = 150_000.0; // Prestação de 1.200

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            financing.setTotalAmount(invalidTotalAmount);
        });
    }
    
    
    @Test
    public void setIncomeShouldUpdateValueWhenDataIsValid() {
        Financing financing = FinancingFactory.createValidFinancing();
        double expectedValue = 3_000.0;

        financing.setIncome(expectedValue);

        Assertions.assertEquals(expectedValue, financing.getIncome(), DELTA);
    }
    
    
    @Test
    public void setMonthsShouldUpdateValueWhenDataIsValid() {
        Financing financing = FinancingFactory.createValidFinancing();
        int expectedValue = 90;

        financing.setMonths(expectedValue);

        Assertions.assertEquals(expectedValue, financing.getMonths());
    }

    @Test
    public void setMonthsShouldThrowExceptionWhenDataIsInvalid() {
        Financing financing = FinancingFactory.createValidFinancing();
        int invalidMonths = 40; // Prestação de 2.000

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            financing.setMonths(invalidMonths);
        });
    }
    
    
    @Test
    public void entryShouldCalculateDownPaymentCorrectly() {
        Financing financing = FinancingFactory.createValidFinancing();
        double expectedValue = 20_000.0; // 20% de 100.000

        double result = financing.entry();

        Assertions.assertEquals(expectedValue, result, DELTA);
    }
    

    @Test
    public void setIncomeShouldThrowExceptionWhenDataIsInvalid() {
        Financing financing = FinancingFactory.createValidFinancing();
        double invalidIncome = 1_000.0; // Limite de 500 para uma prestação de 800

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            financing.setIncome(invalidIncome);
        });
    }

    
    
    

    @Test
    public void quotaShouldCalculateInstallmentCorrectly() {
        Financing financing = FinancingFactory.createValidFinancing();
        double expectedValue = 800.0; // (100.000 - 20.000) / 100

        double result = financing.quota();

        Assertions.assertEquals(expectedValue, result, DELTA);
    }

    
    
	
}
