package com.schedulerx.utils;

import java.lang.reflect.Array;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class Stack<T> {
  private final T[] arr;
  private int top;

  @SuppressWarnings("unchecked")
  public Stack(final int size) {
    arr = (T[]) Array.newInstance(Object.class, size);
    top = 0;
  }

  public void push(T element) {
    arr[top++] = element;
  }

  public T pop() {
    if (top <= 0) {
      log.error("Can't pop from an empty stack!");
      return null;
    }
    return arr[--top];
  }

  public void show() {
    val sb = new StringBuilder("Elements in the stack are:\t");
    for (int i = 0; i < top; i++) {
      sb.append(arr[i].toString());
      if (i < top - 1) {
        sb.append(", ");
      }
    }
    log.info(sb.toString());
  }
}
