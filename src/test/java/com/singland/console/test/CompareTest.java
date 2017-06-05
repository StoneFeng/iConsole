package com.singland.console.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompareTest {

	public static void main(String[] args) {
		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person("fwx425430"));
		personList.add(new Person("cwx335613"));
		personList.add(new Person("hwx375008"));
		personList.sort(new Person());
		for (Person p : personList) {
			System.out.println(p.getBadgeNo());
		}
	}

}

class Person implements Comparator<Person> {
	private String badgeNo;
	
	public Person() {
		super();
	}

	public Person(String badgeNo) {
		super();
		this.badgeNo = badgeNo;
	}

	public String getBadgeNo() {
		return badgeNo;
	}

	public void setBadgeNo(String badgeNo) {
		this.badgeNo = badgeNo;
	}

	@Override
	public int compare(Person arg0, Person arg1) {
		return arg0.getBadgeNo().toLowerCase().compareTo(arg1.getBadgeNo().toLowerCase());
	}
	
}