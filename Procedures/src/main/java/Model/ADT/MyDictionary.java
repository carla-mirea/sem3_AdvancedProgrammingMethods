package Model.ADT;

import Model.Exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V>{
    private Map<K, V> map;

    public MyDictionary(){
        this.map = new HashMap<>();
    }
    @Override
    public void add(K key, V intValue) throws ADTException {
        if (map.containsKey(key)) {
            throw new ADTException("Element already exists");
        }
        map.put(key, intValue);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!map.containsKey(key)){
            throw new ADTException("Element does not exists!");
        }
    }

    @Override
    public void update(K key, V new_value) {
        map.put(key, new_value);
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<K, V> content) {
        map = content;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (Map.Entry<K, V> el: map.entrySet()){
            str.append(el.getKey()).append("-").append(el.getValue()).append(" ");
        }
        return str.toString();
    }

    public void put(K key, V value) {
        synchronized (this) {
            this.map.put(key, value);
        }
    }

}
