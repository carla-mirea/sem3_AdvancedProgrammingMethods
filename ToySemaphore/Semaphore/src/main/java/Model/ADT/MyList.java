package Model.ADT;

import Model.Exceptions.ADTException;

import java.util.ArrayList;

public class MyList<T> implements IList<T>{
    private ArrayList<T> list;
    public MyList(){
        list = new ArrayList<>();
    }
    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public void remove(T element) throws ADTException {
        if (list.contains(element)){
            list.remove(element);
        }
        else
            throw new ADTException("Element " + element + " not found ");
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get_element(int position) throws ADTException {
        if (list.size() < position)
            throw new ADTException("Index out of bounds");
        else
            return list.get(position);
    }

    @Override
    public ArrayList<T> get_list() {
        return list;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(T el: list){
            str.append(el).append(" ");
        }
        return str.toString();
    }
}
