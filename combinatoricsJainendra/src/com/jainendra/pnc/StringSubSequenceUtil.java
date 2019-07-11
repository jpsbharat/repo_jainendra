package com.jainendra.pnc;

import java.util.Set;
import java.util.TreeSet;

public class StringSubSequenceUtil {

	public static void printResult(Set<String> result) {
		System.out.println(result.size());
		System.out.println(result);
		System.out.println();
	}

	public static void main(String[] args) {
		String str = "aeiou";
		testCombinationWithRepetition(str);
		testKLengthCombinationWithRepetition(str, 3);
		testCombination(str);
	}

	public static void testCombinationWithRepetition(String str) {
		Set<String> result = getAllCombinationOfStringWithRepeationRecursively(str);
		printResult(result);
	}

	public static void testKLengthCombinationWithRepetition(String str, int k) {
		Set<String> result = getAllKCombinationOfStringWithRepeationRecursively(
				str, k);
		printResult(result);
	}

	public static void testCombination(String str) {
		Set<String> result = getAllCombinationOfStringRecursively(str);
		printResult(result);
		result = createAllCombinationOfStringRecursivelyV1(str);
		printResult(result);
	}

	public static Set<String> getAllKCombinationOfStringWithRepeationRecursively(
			String str, int k) {
		char[] value = str.toCharArray();
		Set<String> result = new TreeSet<>();
		createAllKCombinationOfStringWithRepeationRecursively(result, 0, value,
				"", k);
		return result;
	}

	private static void createAllKCombinationOfStringWithRepeationRecursively(
			Set<String> result, int index, char[] input, String prefix, int k) {
		if (k == 0) {
			result.add(prefix);
			return;
		}

		for (int i = index; i < input.length; ++i) {
			String newPrefix = prefix + input[i];
			createAllKCombinationOfStringWithRepeationRecursively(result, i,
					input, newPrefix, k - 1);
		}
	}

	public static Set<String> getAllCombinationOfStringWithRepeationRecursively(
			String str) {
		char[] value = str.toCharArray();
		Set<String> result = new TreeSet<>();
		createAllCombinationOfStringWithRepeationRecursively(result, 0, value,
				"");
		return result;
	}

	private static void createAllCombinationOfStringWithRepeationRecursively(
			Set<String> result, int index, char[] input, String prefix) {
		if (prefix.length() > input.length)
			return;
		result.add(prefix);
		for (int i = index; i < input.length; ++i) {
			String newPrefix = prefix + input[i];
			createAllCombinationOfStringWithRepeationRecursively(result, i,
					input, newPrefix);
		}
	}

	public static Set<String> getAllCombinationOfStringRecursively(String str) {
		TreeSet<String> result = new TreeSet<>();
		char[] value = str.toCharArray();
		createAllCombinationOfStringRecursively(result, 0, value, "");
		return result;
	}

	private static void createAllCombinationOfStringRecursively(
			Set<String> result, int index, char[] input, String prefix) {
		if (prefix.length() > input.length)
			return;
		result.add(prefix);
		for (int i = index; i < input.length; ++i) {
			String newPrefix = prefix + input[i];
			createAllCombinationOfStringRecursively(result, i + 1, input,
					newPrefix);
		}
	}

	public static TreeSet<String> createAllCombinationOfStringRecursivelyV1(
			String input) {
		TreeSet<String> result = new TreeSet<>();
		String curr = "";
		createAllCombinationOfStringRecursivelyV1(result, input, 0, curr);
		return result;
	}

	private static void createAllCombinationOfStringRecursivelyV1(
			TreeSet<String> result, String input, int index, String curr) {
		int n = input.length();
		result.add(curr);
		for (int i = index; i < n; i++) {
			curr += input.charAt(i);
			createAllCombinationOfStringRecursivelyV1(result, input, i + 1,
					curr);
			curr = curr.substring(0, curr.length() - 1);
		}
	}
}
