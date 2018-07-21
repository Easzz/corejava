package com.easzz.corejava.refactor.ch1;

/**
 * Created by easzz on 2017/12/9 11:13
 */
public class NewReleasePrice extends AbstractPrice {
	@Override
	public double getDirPrice(int daysRented) {
		return (double) (daysRented * 3);
	}

	@Override
	int getFrequentRenterPoints(int daysRented) {
		return (daysRented > 1) ? 2 : 1;
	}
}
