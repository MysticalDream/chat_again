package models;

import javafx.scene.control.Labeled;

public class Tip {
	private Labeled label;
	private String tipContent;
	private long showTime;
	
	public Tip(Labeled label, String tipContent, long showTime) {
		super();
		this.label = label;
		this.tipContent = tipContent;
		this.showTime = showTime;
	}

	public Labeled getLabel() {
		return label;
	}

	public void setLabel(Labeled label) {
		this.label = label;
	}

	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}

	public long getShowTime() {
		return showTime;
	}

	public void setShowTime(long showTime) {
		this.showTime = showTime;
	}
}
