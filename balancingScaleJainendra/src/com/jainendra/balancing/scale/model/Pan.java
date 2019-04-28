package com.jainendra.balancing.scale.model;

public class Pan extends ScaleNode {

	public Pan() {
		this.left = null;
		this.right = null;
	}

	public void calculate() {
		this.blancingInfo = "";
	}

	public void setLeft(ScaleNode left) {
		this.left = null;
	}

	public void setRight(ScaleNode right) {
		this.right = null;
	}

	public int getWeight() {
		this.totalWeight = this.weight;
		this.currentWeight = this.weight;
		return this.weight;
	}

	public boolean isMass() {
		return true;
	}
}
