package com.easzz.corejava.refactor.ch1;

/**
 * Created by easzz on 2017/11/18 21:54
 */
public class Movie {
	public static final int CHILDRENS = 2;
	public static final int REGULAR = 0;
	public static final int NEW_RELEASE = 1;

	private String _title;
	private AbstractPrice abstractPrice;

	public Movie(String _title, int _priceCode) {
		this._title = _title;
		set_priceCode(_priceCode);
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	private void set_priceCode(int _priceCode) {
		switch (_priceCode) {
			case REGULAR:
				abstractPrice = new RegularPrice();
				break;
			case CHILDRENS:
				abstractPrice = new ChildrensPrice();
				break;
			case NEW_RELEASE:
				abstractPrice = new NewReleasePrice();
				break;
			default:
				throw new IllegalArgumentException("Incorrect Price Code");
		}
	}

	public double countAmount(int daysRented) {
		return abstractPrice.getDirPrice(daysRented);
	}

	public int countFrequentRenterPoints(int daysRented) {

		return abstractPrice.getFrequentRenterPoints(daysRented);
	}
}
