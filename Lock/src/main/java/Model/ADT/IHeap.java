package Model.ADT;

import Model.Exceptions.ADTException;

import java.util.Map;

public interface IHeap<V> {
        int allocate(V value);
        void update(int address, V value);
        V get(int address);
        void deallocate(int address);
        boolean contains(int address);
        Map<Integer, V> getContent();
        void setContent(Map<Integer, V> content);
        public void add(Integer id, V value) throws ADTException;
}
