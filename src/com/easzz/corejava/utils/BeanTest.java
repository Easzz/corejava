package com.easzz.corejava.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.util.Date;

/**
 * Created by easzz on 2017/11/9 15:11
 */
public class BeanTest {
	public static void main(String[] args) throws BeansException, InterruptedException {
		User user = new User();
		user.setAge(11);
		user.setName("mike");
		Date date=new Date();
		user.setBirthday(date);
		user.setSex("男");
		Address address=new Address();
		address.setAddr("武汉");
		user.setAddress(address);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		System.out.println(userDto);
		Thread.sleep(2000);
		address.setAddr("杭州");

		System.out.println(userDto);
	}

}

class User {
	private String name;
	private Integer age;
	private Date birthday;
	private String sex;
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", birthday=" + birthday +
				", sex='" + sex + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}

class UserDto {
	private String name;
	private Integer age;
	private Date birthday;
	private String state;
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserDto{" +
				"name='" + name + '\'' +
				", age=" + age +
				", birthday=" + birthday +
				", state='" + state + '\'' +
				", address=" + address +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
class Address {
	private String addr;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "Address{" +
				"addr='" + addr + '\'' +
				'}';
	}
}