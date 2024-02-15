package Model.ADT;

import Model.Exceptions.ADTException;
import Model.Exceptions.MyException;

import java.util.Stack;

public interface IStack<T> {
    public T pop() throws ADTException;
    public void push(T value);
    boolean isEmpty();

    Stack<T> getStack();
    T peek() throws MyException;
    IStack<T> clone();
}
