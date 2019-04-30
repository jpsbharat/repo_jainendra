package com.jainendra.pnc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PermutationAndCombinationUtil {

	public static <T> Set<List<T>> getAllCombinationOfGivenSize(List<T> inputList, int k) {
		Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
		List<T> outputList = new ArrayList<T>(k);
		for (int i = 0; i < k; i++) {
			outputList.add(null);
		}
		getAllCombinationOfGivenSize0(inputList, k, 0, 0, outputList, subsetsOfInputList);
		return subsetsOfInputList;
	}

	private static <T> void getAllCombinationOfGivenSize0(List<T> inputList,
			int givenSize, int currentIndex, int startIndex,
			List<T> outputList, Set<List<T>> subsetsOfInputList) {
		if (currentIndex == givenSize) {
			List<T> tempList = new ArrayList<T>(givenSize);
			for (int i = 0; i < givenSize; i++) {
				tempList.add(outputList.get(i));
			}
			subsetsOfInputList.add(tempList);
			return;
		}

		for (int i = startIndex; i < inputList.size(); i++) {
			outputList.set(currentIndex, inputList.get(i));
			getAllCombinationOfGivenSize0(inputList, givenSize, currentIndex + 1, i + 1, outputList, subsetsOfInputList);
		}
	}

	public static <T> List<Pair<T, T>> getAllPairs(List<T> inputList) {
		List<Pair<T, T>> result = new ArrayList<Pair<T, T>>();
		for (int i = 0; i < inputList.size() - 1; i++) {
			for (int j = i + 1; j < inputList.size(); j++) {
				result.add(new Pair<T, T>(inputList.get(i), inputList.get(j)));
			}
		}
		return result;
	}

	public int getAllSubArraySum(List<Integer> inputList) {
		int n = inputList.size();
		int totalSum = 0;
		for (int i = 0; i < n; i++) {
			totalSum += inputList.get(i) * (n - i) * (i + 1);
		}
		return totalSum;
	}

	public static <T> List<List<T>> getAllSubListOfList(List<T> inputList) {
		List<List<T>> result = new ArrayList<List<T>>();
		for (int i = 0; i < inputList.size(); i++) {
			for (int j = i + 1; j <= inputList.size(); j++) {
				List<T> tmp = new ArrayList<T>();
				for (int k = i; k < j; k++) {
					tmp.add(inputList.get(k));
				}
				result.add(tmp);
			}
		}
		return result;
	}

	public static <T> List<List<T>> getAllSubArrayOfArray(T[] inputArray) {
		int l = inputArray.length;
		List<List<T>> result = new ArrayList<List<T>>();
		for (int i = 0; i < l; i++) {
			for (int j = i + 1; j <= l; j++) {
				List<T> tmp = new ArrayList<T>();
				for (int k = i; k < j; k++) {
					tmp.add(inputArray[k]);
				}
				result.add(tmp);
			}
		}
		return result;
	}

	public static <T> Set<List<T>> getAllPermutations(List<T> inputList) {
		Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
		getAllPermutations0(subsetsOfInputList, inputList, 0, inputList.size());
		return subsetsOfInputList;
	}

	private static <T> void getAllPermutations0(
			Set<List<T>> subsetsOfInputList, List<T> inputList, int index,
			int size) {
		int x;
		if (index == size) {
			List<T> outputList = new ArrayList<>();
			for (int i = 0; i < inputList.size(); i++) {
				outputList.add(inputList.get(i));
			}
			subsetsOfInputList.add(outputList);
		} else {
			for (x = index; x < size; x++) {
				T temp = inputList.get(index);
				inputList.set(index, inputList.get(x));
				inputList.set(x, temp);
				getAllPermutations0(subsetsOfInputList, inputList, index + 1,
						size);
				temp = inputList.get(index);
				inputList.set(index, inputList.get(x));
				inputList.set(x, temp);
			}
		}
	}

	public static <T> Set<List<T>> getPowerSet(List<T> inputList) {
		Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
		List<T> outputList = new ArrayList<>();
		getPowerSet0(subsetsOfInputList, inputList, outputList, 0);
		subsetsOfInputList.add(outputList);

		return subsetsOfInputList;
	}

	private static <T> void getPowerSet0(Set<List<T>> subsetsOfInputList,
			List<T> inputList, List<T> outputList, int index) {
		subsetsOfInputList.add(new ArrayList<T>(outputList));
		for (int i = index; i < inputList.size(); i++) {
			outputList.add(inputList.get(i));
			getPowerSet0(subsetsOfInputList, inputList, outputList, i + 1);
			outputList.remove(outputList.size() - 1);
		}
	}

	public static List<List<Integer>> getFactors(int n) {
		List<List<Integer>> result = new ArrayList<>();

		if (n <= 1) {
			return result;
		}

		List<Integer> curr = new ArrayList<>();

		getFactors0(2, 1, n, curr, result);

		return result;
	}

	private static void getFactors0(int start, int product, int n,
			List<Integer> curr, List<List<Integer>> result) {
		if (start > n || product > n) {
			return;
		}

		if (product == n) {
			result.add(new ArrayList<Integer>(curr));
			return;
		}

		for (int i = start; i < n; i++) {
			if (i * product > n) {
				break;
			}

			if (n % (product * i) == 0) {
				curr.add(i);
				getFactors0(i, product * i, n, curr, result);
				curr.remove(curr.size() - 1);
			}
		}
	}

	public static class Pair<F, S> {
		private F first;
		private S second;

		public Pair(F first, S second) {
			super();
			this.first = first;
			this.second = second;
		}

		public F getFirst() {
			return first;
		}

		public void setFirst(F first) {
			this.first = first;
		}

		public S getSecond() {
			return second;
		}

		public void setSecond(S second) {
			this.second = second;
		}

		public String toStringg() {
			return "Pair [first=" + first + ", second=" + second + "]";
		}

		@Override
		public String toString() {
			return "<" + first + ", " + second + ">";
		}
	}
}
