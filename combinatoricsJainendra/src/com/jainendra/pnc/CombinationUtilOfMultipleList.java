package com.jainendra.pnc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CombinationUtilOfMultipleList {

	public static <T> List<List<T>> getAllCombinationsRecursive(List<List<T>> input) {
		List<List<T>> result = new ArrayList<>();
		getAllCombinationsRecursive(input, result, 0, new Stack<T>(),
				input.size());
		return result;
	}

	private static <T> void getAllCombinationsRecursive(List<List<T>> input,
			List<List<T>> result, int listIndex, Stack<T> current, int k) {
		if (current.size() == k) {
			result.add(new ArrayList<T>(current));
			return;
		}

		if (listIndex < input.size()) {
			List<T> currentCollection = input.get(listIndex);
			for (T element : currentCollection) {
				current.push(element);
				getAllCombinationsRecursive(input, result, listIndex + 1,
						current, k);
				current.pop();
			}
		}
	}

	public static <T> List<List<T>> getAllCombinationsIterative(List<List<T>> lists) {
		List<List<T>> result = new ArrayList<List<T>>();
		for (T t : lists.get(0)) {
			List<T> combination = new ArrayList<T>();
			combination.add(t);
			result.add(combination);
		}

		int index = 1;
		List<List<T>> currResult = null;
		while (index < lists.size()) {
			List<T> nextCombination = lists.get(index);
			currResult = new ArrayList<List<T>>();
			for (List<T> firstCombination : result) {
				for (T second : nextCombination) {
					List<T> newList = new ArrayList<T>();
					newList.addAll(firstCombination);
					newList.add(second);
					currResult.add(newList);
				}
			}
			result = currResult;
			index++;
		}
		return result;
	}

	public static <T> List<List<T>> getAllCombinationsIterativeV1(List<List<T>> lists) {
		List<List<T>> result = new ArrayList<List<T>>();
		int n = lists.size();
		int[] indices = new int[n];
		for (int i = 0; i < n; i++) {
			indices[i] = 0;
		}

		while (true) {
			List<T> combination = new ArrayList<>();
			for (int i = 0; i < lists.size(); i++) {
				combination.add(lists.get(i).get(indices[i]));
			}
			result.add(combination);

			int next = n - 1;
			while (next >= 0 && (indices[next] >= lists.get(next).size() - 1)) {
				next--;
			}

			if (next < 0) {
				break;
			}

			indices[next]++;
			for (int i = next + 1; i < n; i++) {
				indices[i] = 0;
			}
		}
		return result;
	}
}
