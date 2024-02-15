package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.Value;

public interface Exp {
    Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException;

    Exp deep_copy();

    Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException;
}
