package com.easzz.corejava.typeinfo;

/**
 * Created by easzz on 2017/11/26 9:38
 */
public class SweetShop {
	public static void main(String[] args) {
		System.out.println("inside main");
		new Candy();
		System.out.println("after create candy");
		try {
			Class.forName("com.easzz.corejava.typeinfo.Gum");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't find Gum");
		}
		System.out.println("after Class.forName(\"Gum\")");
		new Cookie();
		System.out.println("after Create Cookie");
		//inside main
		//Loading Candy
		// af
	}
}

class Candy {
	static {
		System.out.println("Loading Candy");
	}
}

class Gum {
	static {
		System.out.println("Loading Gum");
	}
}

class Cookie {
	static {
		System.out.println("Loading Cookie");
	}
}