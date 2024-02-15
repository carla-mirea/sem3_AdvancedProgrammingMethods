package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class NotExpression implements Exp{
    private final Exp expression;

    public NotExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return expression.typeCheck(typeEnv);
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws ExprException {
        BoolValue value = (BoolValue) expression.eval(table, heap);
        if (!value.getValue())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public Exp deep_copy() {
        return new NotExpression(expression.deep_copy());
    }

    @Override
    public String toString() {
        return String.format("!(%s)", expression);
    }
}