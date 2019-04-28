package com.jainendra.balancing.scale.model;

public abstract class ScaleNode {
	private String name;
	protected ScaleNode left;
	protected ScaleNode right;
	protected ScaleNode parent;
	protected int weight;
	protected int totalWeight;
	protected int currentWeight = 0;
	protected int balancedWeight = 0;
	protected int diff = 0;
	protected String blancingInfo;

	protected ScaleNode() {

	}

	public int getDiff() {
		return diff;
	}

	public String getBlancingInfo() {
		return blancingInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ScaleNode getLeft() {
		return left;
	}

	public void setLeft(ScaleNode left) {
		this.left = left;
	}

	public ScaleNode getRight() {
		return right;
	}

	public void setRight(ScaleNode right) {
		this.right = right;
	}

	public ScaleNode getParent() {
		return parent;
	}

	public void setParent(ScaleNode parent) {
		this.parent = parent;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	protected void evaluateDiff(){
		
	}

	public abstract void calculate();

	public abstract boolean isMass();

	public abstract int getWeight();

	@Override
	public String toString() {
		return "ScaleNode [name=" + name + ", weight=" + weight
				+ ", totalWeight=" + totalWeight + ", currentWeight="
				+ currentWeight + ", balancedWeight=" + balancedWeight
				+ ", diff=" + diff + "]";
	}
}
