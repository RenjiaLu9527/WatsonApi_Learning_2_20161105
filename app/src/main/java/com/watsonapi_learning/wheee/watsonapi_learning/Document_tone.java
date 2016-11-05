package com.watsonapi_learning.wheee.watsonapi_learning;

import java.util.ArrayList;
 

public class Document_tone {
	public ArrayList<Tone_categories> tone_categories;

	Document_tone() {

	}

	Document_tone(ArrayList<Tone_categories> tone_categories) {
		this.tone_categories = tone_categories;
	}

	public void setTone_categories(ArrayList<Tone_categories> tone_categories) {
		this.tone_categories = tone_categories;
	}

	public ArrayList<Tone_categories> getTone_categories() {
		return this.tone_categories;
	}

}