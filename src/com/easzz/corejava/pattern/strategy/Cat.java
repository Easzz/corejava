package com.easzz.corejava.pattern.strategy;

/**
 * Created by easzz on 2017/12/7 9:28
 */
public class Cat implements Comparable {
	private Integer weight;
	private Integer height;

	public Cat(Integer height) {
		this.height = height;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public Integer compare(Object o) {
		if (o instanceof Cat) {
			if (this.getHeight() > ((Cat) o).getHeight()) {
				return 1;
			}
		}
		return 0;
	}
}
