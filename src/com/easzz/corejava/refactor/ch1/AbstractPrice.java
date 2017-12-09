package com.easzz.corejava.refactor.ch1;

/**
 * Created by easzz on 2017/12/9 11:11
 */
public abstract class AbstractPrice {
	/**
	 * 状态模式来表示不同类别
	 * <p>
	 * 根据租期获取价格
	 *
	 * @param daysRented
	 * @return
	 */
	abstract double getDirPrice(int daysRented);

	int getFrequentRenterPoints(int daysRented) {
		return 1;
	}
}
