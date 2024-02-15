package Model.ADT;

import Model.Exceptions.ADTException;

import java.util.Stack;

public interface IStack<T> {
    public T pop() throws ADTException;
    public void push(T value);
    boolean isEmpty();

    Stack<T> getStack();
}
