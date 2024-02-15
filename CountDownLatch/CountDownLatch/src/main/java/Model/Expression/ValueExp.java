package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value val){
        this.e = val;

    }
    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        return e;
    }

    @Override
    public Exp deep_copy() {
        return new ValueExp(e.deep_copy());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    public String toString(){
        return e.toString();
    }
}
