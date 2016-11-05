package com.watsonapi_learning.wheee.watsonapi_learning;

import java.util.ArrayList;

public class Tone_categories {
	private ArrayList<Tones> tones;

	private String category_id;

	private String category_name;

	Tone_categories() {
	}

	public void setTones(ArrayList<Tones> tones) {
		this.tones = tones;
	}

	public ArrayList<Tones> getTones() {
		return this.tones;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_id() {
		return this.category_id;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_name() {
		return this.category_name;
	}

}