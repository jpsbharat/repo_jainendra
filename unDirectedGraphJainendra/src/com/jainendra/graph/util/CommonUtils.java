package com.jainendra.graph.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CommonUtils {
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
}
