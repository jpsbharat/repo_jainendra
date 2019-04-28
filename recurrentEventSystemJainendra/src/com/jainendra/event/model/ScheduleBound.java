package com.jainendra.event.model;

import java.time.LocalDate;

public class ScheduleBound {
	private TimeBound endBound;
	private TimeBound startBound;
	private Integer allowedOccurnces = Integer.MAX_VALUE;

	public ScheduleBound() {
		this.startBound = new TimeBound();
		this.endBound = new TimeBound();
		this.endBound.setDate(LocalDate.MAX);
	}

	public TimeBound getEndBound() {
		return endBound;
	}

	public void setEndBound(TimeBound endBound) {
		this.endBound = endBound;
	}

	public TimeBound getStartBound() {
		return startBound;
	}

	public void setStartBound(TimeBound startBound) {
		this.startBound = startBound;
	}

	public Integer getAllowedOccurnces() {
		return allowedOccurnces;
	}

	public void setAllowedOccurnces(Integer allowedOccurnces) {
		this.allowedOccurnces = allowedOccurnces;
	}

	public static class TimeBound {
		private LocalDate date;

		public TimeBound() {
			date = LocalDate.now();
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}
	}
}
