package com.jainendra.graph.model;

public class NodeAStar<T> extends NodeDecorator<T> {
	private double gScore;
	private double fScore = 0;
	private double x;
	private double y;

	public NodeAStar(NodeBase<T> targetNode) {
		super(targetNode);
	}

	public double getgScore() {
		return gScore;
	}
	public void setgScore(double gScore) {
		this.gScore = gScore;
	}
	public double getfScore() {
		return fScore;
	}
	public void setfScore(double fScore) {
		this.fScore = fScore;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
