package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String s){
        id = s;
    }
    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value result = tbl.lookup(id);
        if (result == null) {
            throw new ExprException("Element with ID '" + id + "' not found in the dictionary");
        }

        return result;
    }

    @Override
    public Exp deep_copy() {
        return new VarExp(id);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    public String toString(){
        return id;
    }
}
