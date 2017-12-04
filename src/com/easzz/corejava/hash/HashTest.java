package com.easzz.corejava.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by easzz on 2017/8/15 16:26
 */
public class HashTest {
	private String abc;

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HashTest hashTest = (HashTest) o;

		return abc != null ? abc.equals(hashTest.abc) : hashTest.abc == null;
	}

	@Override
	public int hashCode() {
		return abc != null ? abc.hashCode() : 0;
	}

	public static void main(String[] args){
	    HashTest a=new HashTest();
	    a.setAbc("aaa");
	    HashTest b=new HashTest();
	    b.setAbc("aaa");
		Map m=new HashMap();
		m.put("ab","12");
		Set<HashTest> set=new HashSet<>();
		set.add(a);
		set.add(b);
		System.out.println(set);
		System.out.println(a.equals(b));


	}
}
