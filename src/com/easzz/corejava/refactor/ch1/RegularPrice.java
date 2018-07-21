package com.easzz.corejava.refactor.ch1;

/**
 * Created by easzz on 2017/12/9 11:13
 */
public class RegularPrice extends AbstractPrice {
	@Override
	public double getDirPrice(int daysRented) {
		double price = 2;
		if (daysRented > 2) {
			price += (daysRented - 2) * 1.5;
		}
		return price;
	}
}
