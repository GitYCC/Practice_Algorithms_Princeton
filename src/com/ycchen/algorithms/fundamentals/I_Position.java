package com.ycchen.algorithms.fundamentals;

import java.lang.IllegalStateException;

public interface I_Position<E> {
	E getElement() throws IllegalStateException;
}
