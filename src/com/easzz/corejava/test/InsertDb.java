package com.easzz.corejava.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by easzz on 2017/11/7 9:26
 */
public class InsertDb {
	public static void main(String[] args) throws IOException {
		long l = System.currentTimeMillis();
		File file = new File("D:\\test.sql");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			for (int i = 2000000; i < 2100000; i++) {
				String data = "insert into student values(" + i + ",'张三" + i + "','男','1977-09-01 00:00:00',95033);\n";
				fos.write(data.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		long l1 = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (l1 - l) + "ms");
	}
}
