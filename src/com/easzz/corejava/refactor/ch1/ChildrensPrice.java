package com.easzz.corejava.refactor.ch1;

/**
 * Created by easzz on 2017/12/9 11:13
 */
public class ChildrensPrice extends AbstractPrice {
	@Override
	public double getDirPrice(int daysRented) {
		double price = 1.5;
		if (daysRented > 3) {
			price += (daysRented - 3) * 1.5;
		}
		return price;
	}
}
