package com.jainendra.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertNumberXToY {

	public static StepResult getMinOperationToConvertNumberX2NumberY(int x, int y) {
		List<String> stepList = new ArrayList<String>();
		Map<Integer, StepResult> cacheTopDownDP = new HashMap<Integer, StepResult>();
		StepResult stepResult = convertSource2Target(x, y, 0, stepList,
				cacheTopDownDP);
		return stepResult;
	}

	private static StepResult convertSource2Target(int source, int target,
			int currCount, List<String> stepList,
			Map<Integer, StepResult> cacheTopDownDP) {
		if (cacheTopDownDP.containsKey(source)) {
			return cacheTopDownDP.get(source);
		}

		if (source == target) {
			StepResult sr = new StepResult();
			sr.setNoOfOperations(currCount);
			sr.setOperationList(new ArrayList<String>(stepList));
			cacheTopDownDP.put(source, sr);
			return sr;
		}

		if (source > target) {
			StepResult sr = cacheTopDownDP.get(source);
			if (sr == null) {
				sr = new StepResult();
				cacheTopDownDP.put(source, sr);
			}
			List<String> steps = new ArrayList<String>(stepList);
			int diff = Math.abs(source - target);
			int onOfSteps = currCount + diff;
			int i = diff;
			int s = source;
			do {
				steps.add(s + " - 1 = " + (s - 1) + System.lineSeparator());
				s = s - 1;
				i--;
			} while (i > 0);
			sr.setOperationList(steps);
			sr.setNoOfOperations(onOfSteps);
			return sr;
		}

		if (source <= 0 && target > 0) {
			StepResult sr = new StepResult();
			sr.setNoOfOperations(Integer.MAX_VALUE);
			cacheTopDownDP.put(source, sr);
			return sr;
		}

		int nextSource1 = source * 2;
		int nextSource2 = (source - 1) * 2;
		stepList.add(source + " - 1 = " + (source - 1) + System.lineSeparator()
				+ (source - 1) + " * 2 = " + nextSource2);
		StepResult temp1 = convertSource2Target((source - 1) * 2, target,
				currCount + 2, stepList, cacheTopDownDP);
		stepList.remove(stepList.size() - 1);
		stepList.add(source + " * 2 = " + nextSource1);
		StepResult temp2 = convertSource2Target(source * 2, target,
				currCount + 1, stepList, cacheTopDownDP);
		stepList.remove(stepList.size() - 1);
		if (temp1 != null && temp2 != null) {
			if (temp1.getNoOfOperations() < temp2.getNoOfOperations()) {
				return temp1;
			} else {
				return temp2;
			}
		} else if (temp1 != null) {
			return temp1;
		} else {
			return temp2;
		}
	}

	public static class StepResult {
		private int noOfOperations;
		private List<String> operationList;

		public int getNoOfOperations() {
			return noOfOperations;
		}

		public void setNoOfOperations(int steps) {
			this.noOfOperations = steps;
		}

		public List<String> getOperationList() {
			return operationList;
		}

		public void setOperationList(List<String> stepList) {
			this.operationList = stepList;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("No of operations = " + noOfOperations
					+ System.lineSeparator());
			sb.append("Details of operations:" + System.lineSeparator());
			for (String str : this.operationList) {
				sb.append(str + System.lineSeparator());
			}
			return sb.toString();
		}
	}
}
