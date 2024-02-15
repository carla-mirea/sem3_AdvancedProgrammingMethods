package Model.ADT;

import Model.Exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K, V> {
    public void add(K name, V intValue) throws ADTException;
    void remove(K key) throws ADTException;
    public void update(K key, V new_value);
    public V lookup(K key);
    boolean isDefined(K key);

    Map<K, V> getContent();

    void setContent(Map<K, V> content);

    void put(K key, V value);


}
