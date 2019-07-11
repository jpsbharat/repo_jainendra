package com.jainendra.pnc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GeneralCombinationUtil {

	public static <T> List<List<T>> getKLengthCombinationIterative(
			List<T> input, int r) {
		int n = input.size();
		List<List<T>> combinations = new ArrayList<>();
		Integer[] selectedIndices = new Integer[r];
		for (int i = 0; i < r; i++) {
			selectedIndices[i] = i;
		}

		while (selectedIndices[r - 1] < n) {
			List<T> combination = new ArrayList<>();
			for (int index : selectedIndices) {
				combination.add(input.get(index));
			}
			combinations.add(combination);
			int t = r - 1;
			while (t != 0 && selectedIndices[t] == n - r + t) {
				t--;
			}
			selectedIndices[t]++;
			for (int i = t + 1; i < r; i++) {
				selectedIndices[i] = selectedIndices[i - 1] + 1;
			}
		}
		return combinations;
	}

	public static <T> List<List<T>> getKLengthCombinationRecursive(
			List<T> inputList, int k) {
		List<List<T>> subsetsOfInputList = new ArrayList<List<T>>();
		List<T> outputList = new ArrayList<T>(k);
		for (int i = 0; i < k; i++) {
			outputList.add(null);
		}
		recursiveKLengthCombinationHelper(inputList, k, 0, 0, outputList,
				subsetsOfInputList);
		return subsetsOfInputList;
	}

	private static <T> void recursiveKLengthCombinationHelper(
			List<T> inputList, int givenSize, int currentIndex, int startIndex,
			List<T> outputList, List<List<T>> subsetsOfInputList) {
		if (currentIndex == givenSize) {
			List<T> tempList = new ArrayList<T>(givenSize);
			for (int i = 0; i < givenSize; i++) {
				tempList.add(outputList.get(i));
			}
			subsetsOfInputList.add(tempList);
		} else {
			for (int i = startIndex; i < inputList.size(); i++) {
				outputList.set(currentIndex, inputList.get(i));
				recursiveKLengthCombinationHelper(inputList, givenSize,
						currentIndex + 1, i + 1, outputList, subsetsOfInputList);
			}
		}
	}

	public static <T> Set<List<T>> getPowerSetRecursive(List<T> inputList) {
		Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
		List<T> outputList = new ArrayList<>();
		recursivePowerSetHelper(subsetsOfInputList, inputList, outputList, 0);
		subsetsOfInputList.add(outputList);
		return subsetsOfInputList;
	}

	private static <T> void recursivePowerSetHelper(
			Set<List<T>> subsetsOfInputList, List<T> inputList,
			List<T> outputList, int index) {
		subsetsOfInputList.add(new ArrayList<T>(outputList));
		for (int i = index; i < inputList.size(); i++) {
			outputList.add(inputList.get(i));
			recursivePowerSetHelper(subsetsOfInputList, inputList, outputList,
					i + 1);
			outputList.remove(outputList.size() - 1);
		}
	}

	public static <T> List<List<T>> getPowerSetIterative(List<T> input) {
		List<List<T>> powerSet = new ArrayList<>();
		int numSubSets = 1 << input.size();
		for (int i = 0; i < numSubSets; i++) {
			List<T> subSet = new ArrayList<>();
			for (int j = i, k = 0; j > 0; j >>= 1, k++) {
				if ((j & 1) == 1) {
					subSet.add(input.get(k));
				}
			}
			powerSet.add(subSet);
		}
		return powerSet;
	}

	public static <T> List<List<T>> getPowerSetIterativeV1(List<T> input) {
		List<List<T>> powerSet = new ArrayList<List<T>>();
		powerSet.add(new ArrayList<T>());
		for (T item : input) {
			List<List<T>> currPowerSet = new ArrayList<List<T>>();
			for (List<T> subset : powerSet) {
				currPowerSet.add(subset);
				List<T> nextSubSet = new ArrayList<T>(subset);
				nextSubSet.add(item);
				currPowerSet.add(nextSubSet);
			}
			powerSet = currPowerSet;
		}
		return powerSet;
	}

	public static <T> List<List<T>> getPowerSetIterativeV2(List<T> input) {
		List<List<T>> powerSet = new ArrayList<List<T>>();
		int k = input.size();
		List<List<T>> kResult = null;
		while (k > 0) {
			kResult = getKLengthCombinationIterative(input, k);
			k--;
			powerSet.addAll(kResult);
		}
		return powerSet;
	}
}
