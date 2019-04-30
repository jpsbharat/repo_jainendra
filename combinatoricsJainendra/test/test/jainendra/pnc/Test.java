package test.jainendra.pnc;

import java.util.ArrayList;
import java.util.List;

import com.jainendra.pnc.PermutationAndCombinationUtil;
import com.jainendra.pnc.PermutationAndCombinationUtil.Pair;

public class Test {
	public static void main(String[] args) {
		List<Character> inputList = new ArrayList<Character>();
		inputList.add('a');
		inputList.add('b');
		inputList.add('c');
		inputList.add('d');
		List<Pair<Character, Character>> result = PermutationAndCombinationUtil.getAllPairs(inputList);
		System.out.println(result);
		System.out.println(PermutationAndCombinationUtil.getAllSubListOfList(inputList));
		System.out.println(PermutationAndCombinationUtil.getPowerSet(inputList));
		System.out.println(PermutationAndCombinationUtil.getAllCombinationOfGivenSize(inputList, 3));
	}

	public static void test() {
		List<Integer> inputList = new ArrayList<Integer>();
		inputList.add(1);
		inputList.add(2);
		inputList.add(3);
		inputList.add(4);
		System.out.println(PermutationAndCombinationUtil.getAllSubListOfList(inputList));
		System.out.println(PermutationAndCombinationUtil.getAllPairs(inputList));
		System.out.println(PermutationAndCombinationUtil.getAllPermutations(inputList));
	}
}
