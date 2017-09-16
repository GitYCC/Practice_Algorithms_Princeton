package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_UF;
import java.lang.IndexOutOfBoundsException;



public abstract class AbstractUF implements I_UF {
	protected int count;
	
	abstract public void union(int p,int q);
	abstract public int find(int p);
	abstract protected void validate(int p) throws IndexOutOfBoundsException;
	abstract public boolean connected(int p,int q);
	public int count() {return count;}
	 
}
 