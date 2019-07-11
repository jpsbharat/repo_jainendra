package com.jainendra.pnc;

import java.util.Set;
import java.util.TreeSet;

public class StringSubStringUtil {

	public static void printResult(Set<String> result) {
		System.out.println(result.size());
		System.out.println(result);
		System.out.println();
	}

	public static void main(String[] args) {
		String str = "aeiou";
		testAllSubArray(str);
		testAllKLengthSubArray(str, 3);
	}

	public static void testAllSubArray(String str) {
		Set<String> result = getAllSubStringIterative(str);
		printResult(result);
		result = getAllSubStringIterativeV1(str);
		printResult(result);
		result = getAllSubStringIterativeV2(str);
		printResult(result);
	}

	public static void testAllKLengthSubArray(String str, int k) {
		Set<String> result = getAllKLengthSubString(str, 3);
		printResult(result);
	}

	public static Set<String> getAllSubStringIterative(String str) {
		char[] value = str.toCharArray();
		StringBuilder sb = null;
		TreeSet<String> result = new TreeSet<>();
		int n = value.length;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j <= n; j++) {
				sb = new StringBuilder(n);
				for (int k = i; k < j; k++) {
					sb.append(value[k]);
				}
				result.add(sb.toString());
			}
		}
		return result;
	}

	public static Set<String> getAllSubStringIterativeV1(String input) {
		TreeSet<String> result = new TreeSet<>();
		int n = input.length();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j <= n; j++) {
				result.add(input.substring(i, j));
			}
		}
		return result;
	}

	public static Set<String> getAllSubStringIterativeV2(String input) {
		char[] strArray = input.toCharArray();
		TreeSet<String> result = new TreeSet<>();
		int n = input.length();
		for (int r = 1; r <= n; r++) {
			for (int i = 0; i <= n - r; i++) {
				StringBuilder sb = new StringBuilder();
				for (int k = i; k < (i + r); k++) {
					sb.append(strArray[k]);
				}
				result.add(sb.toString());
			}
		}
		return result;
	}

	public static Set<String> getAllKLengthSubString(String str, int k) {
		char[] value = str.toCharArray();
		StringBuilder sb = null;
		TreeSet<String> result = new TreeSet<>();
		int n = value.length;
		for (int i = 0; i <= n - k; i++) {
			sb = new StringBuilder(k);
			for (int j = 0; j < k; j++) {
				sb.append(value[i + j]);
			}
			result.add(sb.toString());
		}
		return result;
	}
}
