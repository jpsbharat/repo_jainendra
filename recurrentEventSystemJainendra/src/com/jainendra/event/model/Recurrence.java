package com.jainendra.event.model;

public abstract class Recurrence {
	protected int frequency;
	protected ScheduleBound scheduleBound;

	protected Recurrence(int frequency) {
		this.frequency = frequency;
	}

	public int getFrequency() {
		return frequency;
	}

	public ScheduleBound getScheduleBound() {
		return scheduleBound;
	}

	public void setScheduleBound(ScheduleBound scheduleBound) {
		this.scheduleBound = scheduleBound;
	}
}
