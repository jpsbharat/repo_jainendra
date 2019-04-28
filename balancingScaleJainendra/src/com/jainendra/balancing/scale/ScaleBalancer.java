package com.jainendra.balancing.scale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jainendra.balancing.scale.model.Pan;
import com.jainendra.balancing.scale.model.Scale;
import com.jainendra.balancing.scale.model.ScaleNode;

public class ScaleBalancer {
	private int panWeigt;
	private int scaleWeight;
	private Map<String, ScaleNode> scaleMap = null;

	public ScaleBalancer(int scaleWeight, int panWeigt) {
		this.panWeigt = panWeigt;
		this.scaleWeight = scaleWeight;
		this.scaleMap = new TreeMap<String, ScaleNode>();
	}

	public String getBalancingInfo(){
		StringBuilder sb = new StringBuilder();
		for (String key : scaleMap.keySet()) {
			ScaleNode scaleNode = scaleMap.get(key);
			if (scaleNode.getBlancingInfo() != null
					&& !scaleNode.getBlancingInfo().isEmpty()) {
				sb.append(scaleNode.getBlancingInfo() + System.lineSeparator());
			}
		}
		return sb.toString();
	}

	public List<String> getBalancingInfoList(){
		List<String> input = new ArrayList<>();
		for (String key : scaleMap.keySet()) {
			ScaleNode scaleNode = scaleMap.get(key);
			if (scaleNode.getBlancingInfo() != null
					&& !scaleNode.getBlancingInfo().isEmpty()) {
				input.add(scaleNode.getBlancingInfo());
			}
		}
		return input;
	}

	public void addScale(String scaleName, String scaleLeft, String scaleRight) {
		ScaleNode parentNode = createParentScaleNode(scaleName);
		ScaleNode leftNode = createChildScaleNode(parentNode, scaleLeft);
		parentNode.setLeft(leftNode);
		ScaleNode rightNode = createChildScaleNode(parentNode, scaleRight);
		parentNode.setRight(rightNode);
		if (rightNode.isMass() && leftNode.isMass()) {
			parentNode.calculate();
		}
	}

	private ScaleNode createParentScaleNode(String scaleName) {
		ScaleNode parentNode = null;
		if (this.scaleMap.containsKey(scaleName)) {
			parentNode = this.scaleMap.get(scaleName);
		} else {
			parentNode = new Scale();
			parentNode.setName(scaleName);
			parentNode.setWeight(this.scaleWeight);
			this.scaleMap.put(scaleName, parentNode);
		}
		return parentNode;
	}

	private ScaleNode createChildScaleNode(ScaleNode parentNode, String scaleChild) {
		ScaleNode childNode = null;
		try {
			int weightOnPan = Integer.parseInt(scaleChild);
			childNode = new Pan();
			childNode.setWeight(this.panWeigt + weightOnPan);
		} catch (NumberFormatException e) {
			childNode = new Scale();
			childNode.setWeight(this.scaleWeight);
			childNode.setName(scaleChild);
			this.scaleMap.put(scaleChild, childNode);
		}
		childNode.setParent(parentNode);
		return childNode;
	}
}
