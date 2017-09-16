package com.ycchen.algorithms.searching;

import java.util.Objects;

public class Person {
	private final String name;
	private final long info;
	public Person(String name,long info) {
		this.name = name;
		this.info = info;
	}
	
	@Override
	public boolean equals(Object other){
		if (other==this) return true;
		if (other==null) return false;
		if (other.getClass()!=this.getClass()) return false;
		Person that = (Person) other;
		return (that.name.equals(this.name))&&(that.info==this.info);
	}
	
	@Override
	public String toString() {
		return name+" "+info;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(info,name);
	}
	
	public static void main(String[] args) {
		Person a = new Person("Alice",89417);
		Person b = new Person("YCChen",89417);
		Person c = new Person("Lisa",520);
		System.out.println("b = "+b);
		System.out.println("b == a: "+b.equals(a));
		System.out.println("b == b: "+b.equals(b));
		System.out.println("b == c: "+b.equals(c));
		
		
	}
}
