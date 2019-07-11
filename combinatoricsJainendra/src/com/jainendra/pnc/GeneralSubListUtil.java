package com.jainendra.pnc;

import java.util.ArrayList;
import java.util.List;

public class GeneralSubListUtil {

	public static <T> List<Pair<T, T>> getAllPairs(List<T> inputList) {
		List<Pair<T, T>> result = new ArrayList<Pair<T, T>>();
		for (int i = 0; i < inputList.size() - 1; i++) {
			for (int j = i + 1; j < inputList.size(); j++) {
				result.add(new Pair<T, T>(inputList.get(i), inputList.get(j)));
			}
		}
		return result;
	}

	public static int getAllSubArraySum(List<Integer> inputList) {
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
