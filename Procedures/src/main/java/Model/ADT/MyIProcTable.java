package Model.ADT;

import Model.Exceptions.MyException;
import Model.Statement.IStmt;
import javafx.util.Pair;

import java.util.*;

public interface MyIProcTable {
    boolean isDefined(String key);
    void put(String key, Pair<List<String>, IStmt> value);
    Pair<List<String>, IStmt> lookUp(String key) throws MyException;
    void update(String key,  Pair<List<String>, IStmt> value) throws MyException;
    Collection< Pair<List<String>, IStmt>> values();
    void remove(String key) throws MyException;
    Set<String> keySet();
    HashMap<String,  Pair<List<String>, IStmt>> getContent();
    IDictionary<String, Pair<List<String>, IStmt>> deepCopy() throws MyException;
}
