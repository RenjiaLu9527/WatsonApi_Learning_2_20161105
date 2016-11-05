package com.watsonapi_learning.wheee.watsonapi_learning;

public class Tones {
	private String tone_name;

	private String tone_id;

	private double score;

	Tones() {
	}

//	public Tones(String name, String id, double score) {
//
//		this.tone_name = "null";
//		this.tone_id = "null";
//		this.score = -1;
//	}

	public void setTone_name(String tone_name) {
		this.tone_name = tone_name;
	}

	public String getTone_name() {
		return this.tone_name;
	}

	public void setTone_id(String tone_id) {
		this.tone_id = tone_id;
	}

	public String getTone_id() {
		return this.tone_id;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getScore() {
		return this.score;
	}

}