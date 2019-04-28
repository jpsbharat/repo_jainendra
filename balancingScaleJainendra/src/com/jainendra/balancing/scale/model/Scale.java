package com.jainendra.balancing.scale.model;

public class Scale extends ScaleNode {

	public Scale() {
	}

	public void calculate() {
		this.createBalancingInfo();
		if (this.parent != null) {
			this.parent.calculate();
		}
	}

	private void createBalancingInfo() {
		if (this.left != null && this.right != null) {
			int leftWeight = this.left.getWeight();
			int rightWeight = this.right.getWeight();
			this.evaluateDiff();
			if (leftWeight > rightWeight) {
				this.blancingInfo = this.getName() + ",0," + this.diff;
			} else if (leftWeight < rightWeight) {
				this.blancingInfo = this.getName() + "," + this.diff + ",0";
			} else {
				this.blancingInfo = this.getName() + ",0,0";
			}
		}
	}

	protected void evaluateDiff() {
		if (this.left.isMass() && this.right.isMass()) {
			this.diff = Math.abs(this.left.weight - this.right.weight);
			this.balancedWeight = (this.left.weight + this.right.weight + this.diff) / 2;
		} else {
			if (this.left.currentWeight > this.right.currentWeight) {
				this.balancedWeight = this.left.currentWeight;
				this.diff = Math.abs(this.balancedWeight - this.right.currentWeight);
			} else if (this.left.currentWeight < this.right.currentWeight) {
				this.balancedWeight = this.right.currentWeight;
				this.diff = Math.abs(this.balancedWeight - this.left.currentWeight);
			} else {
				this.balancedWeight = this.right.currentWeight;
				this.diff = Math.abs(this.balancedWeight - this.left.currentWeight);
			}
		}
		this.currentWeight = 2 * (this.balancedWeight) + this.weight;
	}

	public boolean isMass() {
		return false;
	}

	public int getWeight() {
		this.totalWeight = this.weight
				+ ((this.left != null) ? this.left.getWeight() : 0)
				+ ((this.right != null) ? this.right.getWeight() : 0);
		return this.totalWeight;
	}
}
