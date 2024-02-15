package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;

public class MULExpression implements Exp{
    private final Exp expression1;
    private final Exp expression2;

    public MULExpression(Exp expression1, Exp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()))
            return new IntType();
        else
            throw new MyException("Expressions in the MUL should be int!");
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws ExprException {
        Exp converted = new ArithExp('-',
                new ArithExp('*', expression1, expression2),
                new ArithExp('+', expression1, expression2));
        return converted.eval(table, heap);
    }

    @Override
    public Exp deep_copy() {
        return new MULExpression(expression1.deep_copy(), expression2.deep_copy());
    }

    @Override
    public String toString() {
        return String.format("MUL(%s, %s)", expression1, expression2);
    }
}
