package com.jainendra;

import java.util.ArrayList;
import java.util.List;

public class TeamConstruction {

	public static void main(String[] args) {
		List<Integer> score = new ArrayList<>();
		score.add(30);
		score.add(20);
		score.add(28);
		score.add(8);
		score.add(2);
		score.add(38);
		score.add(35);
		score.add(25);
		int result = teamScore(score, 3, 3);
		System.out.println(result);
	}

	public static int teamScore(List<Integer> score, int team, int m) {
		List<Integer> selection = getSelection(score, team, m);
		System.out.println(selection);
		int result = 0;
		for (int i : selection) {
			result += i;
		}
		return result;
	}

	public static List<Integer> getSelection(List<Integer> score, int team, int m) {
		List<Integer> selection = new ArrayList<>();
		makeSelection(new ArrayList<Integer>(score), team, m, selection);
		return selection;
	}

	public static void makeSelection(List<Integer> score, int team, int m,
			List<Integer> selection) {
		if (team == 0) {
			return;
		}
		int result = 0;
		int n = score.size();
		int selectedIndex = 0;
		if (m > n) {
			for (int i = 0; i < n; i++) {
				if (result < score.get(i)) {
					result = score.get(i);
					selectedIndex = i;
				}
			}
		} else {
			for (int i = 0; i < m; i++) {
				if (result < score.get(i)) {
					result = score.get(i);
					selectedIndex = i;
				}
			}

			int k = n - m;
			for (int i = n - 1; i >= k; i--) {
				if (result < score.get(i)) {
					result = score.get(i);
					selectedIndex = i;
				}
			}
		}
		score.remove(selectedIndex);
		selection.add(result);
		makeSelection(score, team - 1, m, selection);
	}
}
