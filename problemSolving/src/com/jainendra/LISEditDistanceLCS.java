package com.jainendra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LISEditDistanceLCS {

	public static void main(String[] args) {
		Integer[] arr = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		List<Integer> lis = getLis(Arrays.asList(arr));
		System.out.println(lis);

		Integer lisLength = getLisRec(Arrays.asList(arr));
		System.out.println(lisLength);
	}

	public static List<Integer> getLis(List<Integer> input) {
		Map<Integer, List<Integer>> lisTillAnIndexMap = new HashMap<>();
		for (int i = 0; i < input.size(); i++) {
			lisTillAnIndexMap.put(i, new ArrayList<Integer>());
		}

		lisTillAnIndexMap.get(0).add(input.get(0));
		for (int i = 1; i < input.size(); i++) {
			List<Integer> lisTillCurrentIthIndex = lisTillAnIndexMap.get(i);
			for (int j = 0; j < i; j++) {
				List<Integer> lisTillCurrentJthIndex = lisTillAnIndexMap.get(j);
				if (input.get(j) < input.get(i)
						&& lisTillCurrentJthIndex.size() > lisTillCurrentIthIndex
								.size()) {
					lisTillCurrentIthIndex.clear();
					lisTillCurrentIthIndex.addAll(lisTillCurrentJthIndex);
				}
			}
			lisTillCurrentIthIndex.add(input.get(i));
		}

		int j = 0;
		for (int i = 0; i < input.size(); i++) {
			if (lisTillAnIndexMap.get(j).size() < lisTillAnIndexMap.get(i)
					.size()) {
				j = i;
			}
		}

		return lisTillAnIndexMap.get(j);
	}

	public static int getLisRec(List<Integer> input) {
		return getLisRec(input, 0, Integer.MIN_VALUE);
	}

	private static int getLisRec(List<Integer> input, int index, int prev) {
		if (index == input.size()) {
			return 0;
		}

		int excl = getLisRec(input, index + 1, prev);

		int incl = 0;
		if (input.get(index) > prev) {
			incl = 1 + getLisRec(input, index + 1, input.get(index));
		}

		int result = Integer.max(incl, excl);
		return result;
	}

	public static int getMaxLCSLengthRec(String x, String y) {
		Map<String, Integer> cache = new HashMap<>();
		return getMaxLCSLengthRec(x, y, x.length(), y.length(), cache);
	}

	private static int getMaxLCSLengthRec(String x, String y, int m, int n,
			Map<String, Integer> lookup) {
		if (m == 0 || n == 0)
			return 0;

		String key = m + "#" + n;
		if (!lookup.containsKey(key)) {
			if (x.charAt(m - 1) == y.charAt(n - 1)) {
				lookup.put(key,
						getMaxLCSLengthRec(x, y, m - 1, n - 1, lookup) + 1);
			} else {
				lookup.put(key, Integer.max(
						getMaxLCSLengthRec(x, y, m, n - 1, lookup),
						getMaxLCSLengthRec(x, y, m - 1, n, lookup)));
			}
		}
		return lookup.get(key);
	}

	public static int getMaxLCSLengthIter(String x, String y) {
		int[][] lookUpTable = prepareLookupTableForLCS(x, y);
		return lookUpTable[x.length() - 1][y.length() - 1];
	}

	public static String getLCS(String x, String y) {
		int[][] lookUpTable = prepareLookupTableForLCS(x, y);
		return getLCS(x, y, x.length(), y.length(), lookUpTable);
	}

	public static List<String> getAllLCS(String x, String y) {
		int[][] lookUpTable = prepareLookupTableForLCS(x, y);
		return getAllLCS(x, y, x.length(), y.length(), lookUpTable);
	}

	private static String getLCS(String x, String y, int m, int n,
			int[][] lookUpTable) {
		String result = null;
		if (m == 0 || n == 0) {
			result = "";
		} else if (x.charAt(m - 1) == y.charAt(n - 1)) {
			result = getLCS(x, y, m - 1, n - 1, lookUpTable) + x.charAt(m - 1);
		} else if (lookUpTable[m - 1][n] > lookUpTable[m][n - 1]) {
			result = getLCS(x, y, m - 1, n, lookUpTable);
		} else {
			result = getLCS(x, y, m, n - 1, lookUpTable);
		}
		return result;
	}

	private static List<String> getAllLCS(String x, String y, int m, int n,
			int[][] lookUpTable) {
		List<String> result = null;
		if (m == 0 || n == 0) {
			result = new ArrayList<>(Collections.nCopies(1, ""));
		} else if (x.charAt(m - 1) == y.charAt(n - 1)) {
			result = getAllLCS(x, y, m - 1, n - 1, lookUpTable);
			for (int i = 0; i < result.size(); i++) {
				result.set(i, result.get(i) + (x.charAt(m - 1)));
			}
		} else if (lookUpTable[m - 1][n] > lookUpTable[m][n - 1]) {
			result = getAllLCS(x, y, m - 1, n, lookUpTable);
		} else if (lookUpTable[m][n - 1] > lookUpTable[m - 1][n]) {
			result = getAllLCS(x, y, m, n - 1, lookUpTable);
		} else {
			result = getAllLCS(x, y, m - 1, n, lookUpTable);
			List<String> left = getAllLCS(x, y, m, n - 1, lookUpTable);
			result.addAll(left);
		}
		return result;
	}

	private static int[][] prepareLookupTableForLCS(String x, String y) {
		int m = x.length();
		int n = y.length();
		int[][] lookUpTable = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (x.charAt(i - 1) == y.charAt(j - 1)) {
					lookUpTable[i][j] = lookUpTable[i - 1][j - 1] + 1;
				} else {
					lookUpTable[i][j] = Integer.max(lookUpTable[i - 1][j],
							lookUpTable[i][j - 1]);
				}
			}
		}
		return lookUpTable;
	}

	public int editDistanceRec(String s1, String s2) {
		return editDistanceRecursion(s1, s2, s1.length(), s2.length());
	}

	public int editDistanceRecursion(String s1, String s2, int m, int n) {
		int result = 0;
		if (m == 0) {
			result = n;
		} else if (n == 0) {
			result = m;
		} else if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
			result = editDistanceRecursion(s1, s2, m - 1, n - 1);
		} else {
			result = 1 + Math.min(editDistanceRecursion(s1, s2, m, n - 1), // Insert
					Math.min(editDistanceRecursion(s1, s2, m - 1, n), // Remove
							editDistanceRecursion(s1, s2, m - 1, n - 1))); // Replace
		}
		return result;
	}
}
