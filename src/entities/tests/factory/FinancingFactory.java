package entities.tests.factory;

import entities.Financing;

public class FinancingFactory {

	private FinancingFactory() {
	}

	public static Financing createValidFinancing() {
		double totalAmount = 100_000.0;
		double income = 2_000.0;
		int months = 100;

		return new Financing(totalAmount, income, months);
	}
}
