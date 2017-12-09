package com.easzz.corejava.refactor.ch1;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by easzz on 2017/12/9 9:20
 * 计算顾客的消费金额，并打印详单
 * 操作中告诉程序：顾客租了那些影片、租期多长，程序根据租赁时间和影片类型算出费用。
 * 影片分为三类：普通片、儿童片和新片。
 * 除了计算费用，还要为常客计算积分，积分会根据影片种类是是否为新片而有所不同。
 */
public class Customer {
	private String name;
	private Vector<Rental> rentals = new Vector<>();

	public Customer(String name) {
		this.name = name;
	}

	public void addRentals(Rental rental) {
		rentals.add(rental);
	}

	public String getName() {
		return name;
	}

	public String statement() {
		Enumeration rentalsElements = rentals.elements();
		StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\n");
		while (rentalsElements.hasMoreElements()) {
			Rental rental = (Rental) rentalsElements.nextElement();
			//计算积分
			result.append("\t ").append(rental.getMovie().get_title())
					.append("\t").append(String.valueOf(rental.countAount())).append("\n");
		}
		//将总金额提取出来，或许其他地方有可能需要这个值。
		result.append("Amount owed is ").append(String.valueOf(getTotalAmount())).append("\n");
		result.append("You earned ").append(String.valueOf(getTotalFrequentRenterPoints())).append(" frequent renter points");
		return result.toString();
	}

	/**
	 * 获取总金额
	 *
	 * @return
	 */
	private double getTotalAmount() {
		double amount = 0;
		Enumeration<Rental> elements = rentals.elements();
		while (elements.hasMoreElements()) {
			Rental rental = elements.nextElement();
			amount += rental.countAount();
		}
		return amount;
	}

	private int getTotalFrequentRenterPoints() {
		int result = 0;
		Enumeration<Rental> elements = rentals.elements();
		while (elements.hasMoreElements()) {
			Rental rental = elements.nextElement();
			result += rental.countFrequentRenterPoints();
		}
		return result;
	}
}
