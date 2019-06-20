package com.jainendra;

public class StockMaxProfit {

	public static void main(String[] args) {
		int[] prices = { 400, 402, 435, 398, 378, 400, 432, 432, 402 };
		System.out
				.println(getMaxProfitMultipleTransactions(prices, 0, 2, false));
		maxProfitSigleSellDP(prices);
		System.out.println(maxProfitSingleSellRecursive(prices, 0,
				prices.length - 1));
		System.out
				.println(getMaxProfitMultipleTransactions(prices, 0, 1, false));

	}

	public static int getMaxProfitMultipleTransactions(int[] prices,
			int startDay, int maxTransaction, boolean canSell) {
		if (maxTransaction == 0 || startDay == prices.length)
			return 0;
		if (canSell) {
			return Math.max(
					getMaxProfitMultipleTransactions(prices, startDay + 1,
							maxTransaction, true),
					getMaxProfitMultipleTransactions(prices, startDay + 1,
							maxTransaction - 1, false) + prices[startDay]);
		} else {
			return Math.max(
					getMaxProfitMultipleTransactions(prices, startDay + 1,
							maxTransaction, false),
					getMaxProfitMultipleTransactions(prices, startDay + 1,
							maxTransaction, true) - prices[startDay]);
		}
	}

	public static void maxProfitSigleSellDP(int[] prices) {
		int period = prices.length;
		int buyDateIndex = 0;
		int tempIndex = 0;
		int sellDateIndex = 0;
		int current_profit = 0;
		int max_sell_price = prices[period - 1]; // assign the last element
		for (int i = period - 2; i > 0; i--) {
			if (max_sell_price < prices[i]) {
				max_sell_price = prices[i];
				tempIndex = i;
			} else if (max_sell_price > prices[i]) {
				if (current_profit < max_sell_price - prices[i]) {
					current_profit = max_sell_price - prices[i];
					buyDateIndex = i;
					sellDateIndex = tempIndex;
				}
			}
		}
		System.out.println("Maximum Profit(DP): " + current_profit
				+ ", buy date index: " + buyDateIndex + ", sell date index: "
				+ sellDateIndex);
	}

	public static Result maxProfitSingleSellRecursive(int[] prices, int start,
			int end) {
		if (start >= end) {
			return new Result(0, 0, 0);
		}
		int mid = start + (end - start) / 2;
		Result leftResult = maxProfitSingleSellRecursive(prices, start, mid);
		Result rightResult = maxProfitSingleSellRecursive(prices, mid + 1, end);
		int minLeftIndex = getMinIndex(prices, start, mid);
		int maxRightIndex = getMaxIndex(prices, mid, end);
		int centerProfit = prices[maxRightIndex] - prices[minLeftIndex];
		if (centerProfit > leftResult.profit
				&& centerProfit > rightResult.profit) {
			return new Result(centerProfit, minLeftIndex, maxRightIndex);
		} else if (leftResult.profit > centerProfit
				&& rightResult.profit > centerProfit) {
			return leftResult;
		} else {
			return rightResult;
		}
	}

	public static int getMinIndex(int[] A, int i, int j) {
		int min = i;
		for (int k = i + 1; k <= j; k++) {
			if (A[k] < A[min])
				min = k;
		}
		return min;
	}

	public static int getMaxIndex(int[] A, int i, int j) {
		int max = i;
		for (int k = i + 1; k <= j; k++) {
			if (A[k] > A[max])
				max = k;
		}
		return max;
	}

	public static class Result {
		int profit = 0;
		int buyDateIndex = 0;
		int sellDateIndex = 0;

		public Result(int profit, int buyDateIndex, int sellDateIndex) {
			this.profit = profit;
			this.buyDateIndex = buyDateIndex;
			this.sellDateIndex = sellDateIndex;
		}

		@Override
		public String toString() {
			return "Result [profit=" + profit + ", buyDateIndex="
					+ buyDateIndex + ", sellDateIndex=" + sellDateIndex + "]";
		}
	}
}