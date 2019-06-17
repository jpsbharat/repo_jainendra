package com.jainendra;

import java.util.Stack;

public class ReversingADecimalNumber {

	public static int reverse(int number) {
		int result = 0;
		Stack<Integer> digits = new Stack<Integer>();

		int mostSignificantMultiplier = getMostSignificantMultiplier(number);

		int current = number;
		int currentDivisor = mostSignificantMultiplier;
		while (currentDivisor > 0) {
			int digit = current / currentDivisor;
			current = current % currentDivisor;
			currentDivisor = currentDivisor / 10;
			digits.push(digit);
		}

		int currentMultiplier = mostSignificantMultiplier;
		while (!digits.isEmpty()) {
			int digit = digits.pop();
			result = result + digit * currentMultiplier;
			currentMultiplier = currentMultiplier / 10;
		}
		return result;
	}

	public static int getMostSignificantMultiplier(int number) {
		int mostSignificantMultiplier = 1;
		while (mostSignificantMultiplier <= number) {
			mostSignificantMultiplier = mostSignificantMultiplier * 10;
		}
		mostSignificantMultiplier = mostSignificantMultiplier / 10;
		return mostSignificantMultiplier;
	}

	public static void main(String[] args) {
		test(100);
		test(10);
		test(101);
		test(103);
		test(1340);
		test(340608090);
		test(13406);
		test(1340600);
		test(987650);
		test(134);
		test(53267);
		test(5689);
		test(8956789);
		test(5436);
	}

	public static void test(int number) {
		int result = reverse(number);
		System.out.println("Reverse of " + number + " is " + result);
	}
}
