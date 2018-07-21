package com.easzz.corejava.pattern.strategy;

/**
 * Created by easzz on 2017/12/7 9:41
 */
public class Dog implements Comparable {

	private Integer weight;

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public Integer compare(Object o) {
		if (o instanceof Dog) {
			if (this.getWeight() > ((Dog) o).getWeight()) {
				return 1;
			}
		}
		return 0;
	}
}
