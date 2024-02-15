package Model.ADT;

import Model.Exceptions.ADTException;

import java.util.ArrayList;

public interface IList<T> {
    void add(T element);
    void remove(T element) throws ADTException;
    int size();
    T get_element(int position) throws ADTException;

    ArrayList<T> get_list();
}
