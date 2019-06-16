package com.jainendra;

public class DividingWithoutDivisionOperator {
	public static void main(String[] args) {
		Double result = divide(1, 3, 3);
		System.out.println(result);
		result = divide(10, 3, 3);
		System.out.println(result);
		result = divide(100, 3, 3);
		System.out.println(result);
		result = divide(2, 3, 3);
		System.out.println(result);
		result = divide(151, 12, 3);
		System.out.println(result);
		result = divide(151, 13, 3);
		System.out.println(result);
	}

	public static Double divide(int dividend, int divisor, int decimalPlace) {
		String result = getDevision(dividend, divisor, decimalPlace);
		return Double.parseDouble(result);
	}

	private static String getDevision(int dividend, int divisor,
			int decimalPlace) {
		String fraction = "";
		String integer = "";
		if (divisor == dividend) {
			int count = decimalPlace;
			while (count > 0) {
				fraction += "0";
			}
			integer = "1";
		} else if (divisor > dividend) {
			integer = "0";
			fraction = getFraction(dividend * 10, divisor, decimalPlace);
		} else {
			int prev = 0;
			int remainder = 0;
			int quotient = 0;
			for (int i = 1; i <= dividend; i++) {
				int temp = i * divisor;
				if (temp > dividend) {
					break;
				}
				prev = temp;
				quotient = i;
			}
			remainder = dividend - prev;
			integer = "" + quotient;
			fraction = getFraction(remainder * 10, divisor, decimalPlace);
		}
		return integer + "." + fraction;
	}

	private static String getFraction(int dividend, int divisor,
			int decimalPlace) {
		int prev = 0;
		int remainder = 0;
		int quotient = 0;
		for (int i = 1; i <= dividend; i++) {
			int temp = i * divisor;
			if (temp > dividend) {
				break;
			}
			prev = temp;
			quotient = i;
		}
		
		remainder = dividend - prev;
		if (decimalPlace == 1) {
			return "" + quotient;
		} else {
			return quotient
					+ getFraction(remainder * 10, divisor, decimalPlace - 1);
		}
	}
}
