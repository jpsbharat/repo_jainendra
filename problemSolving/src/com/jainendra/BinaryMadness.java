package com.jainendra;

import java.math.BigInteger;
import java.util.Scanner;

public class BinaryMadness {

	public static void main(String[] args) {
		Scanner sc;
		try {
			sc = new Scanner(System.in);
			int noOfTestCases = sc.nextInt();
			while (noOfTestCases > 0) {
				noOfTestCases--;
				BigInteger number = sc.nextBigInteger();
				int oddOneCount = getOddOneCount(number);
				System.out.println(oddOneCount);
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getOddOneCount(BigInteger number) {
		String binary = number.toString(2);
		int oddOneCount = getOddOneCount(binary.toCharArray());
		return oddOneCount;
	}

	public static int getOddOneCount(char[] inputArray) {
		int oddOneCount = 0;
		int l = inputArray.length;
		for (int i = 0; i < l; i++) {
			for (int j = i + 1; j <= l; j++) {
				int count = 0;
				for (int k = i; k < j; k++) {
					if (inputArray[k] == '1') {
						count++;
					}
				}
				if (count % 2 != 0) {
					oddOneCount++;
				}
			}
		}
		return oddOneCount;
	}
}
