package com.easzz.corejava.refactor.ch1;

/**
 * Created by easzz on 2017/12/9 9:19
 */
public class Rental {
	private Movie movie;
	private int daysRented;

	public Rental(Movie movie, int daysRented) {
		this.movie = movie;
		this.daysRented = daysRented;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public int getDaysRented() {
		return daysRented;
	}

	public void setDaysRented(int daysRented) {
		this.daysRented = daysRented;
	}

	/**
	 * 计算租赁不同影片的金额
	 *
	 * @return
	 */
	public double countAount() {
		return getMovie().countAmount(getDaysRented());
	}

	public int countFrequentRenterPoints() {
		return getMovie().countFrequentRenterPoints(daysRented);

	}
}
