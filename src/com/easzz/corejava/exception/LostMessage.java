package com.easzz.corejava.exception;

/**
 * Created by 沈轩 on 2018/7/8 11:42
 */
public class LostMessage {
	void f() throws VeryImportantException {
		throw new VeryImportantException();
	}

	void dispose() throws HoHumException {
		throw new HoHumException();
	}

	public static void main(String[] args) {
		try {
			LostMessage lm = new LostMessage();
			try {
				lm.f();
			} finally {
				lm.dispose();
			}
		} catch (Exception e) {
			System.out.println(e);
		}


	}
}

class VeryImportantException extends Exception {
	@Override
	public String toString() {
		return "A very Important exception";
	}
}

class HoHumException extends Exception {
	@Override
	public String toString() {
		return "A trivial exception";
	}
}


