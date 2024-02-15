package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelationalExp implements Exp{
    private Exp exp1;
    private Exp exp2;
    private int op; // 1 <, 2 <=, 3 ==, 4 !=, 5 >, 6 >=

    public RelationalExp(Exp exp1, Exp exp2, int op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap<Value> heap) throws ExprException {
        Value val1, val2;
        val1 = exp1.eval(symTable, heap);
        val2 = exp2.eval(symTable, heap);
        if (val1.getType().equals(new IntType()) && val2.getType().equals(new IntType())) {
            IntValue intVal1, intVal2;
            intVal1 = (IntValue) val1;
            intVal2 = (IntValue) val2;
            int x = intVal1.getValue();
            int y = intVal2.getValue();
            switch (op) {
                case 1:
                    return new BoolValue(x < y);
                case 2:
                    return new BoolValue(x <= y);
                case 3:
                    return new BoolValue(x == y);
                case 4:
                    return new BoolValue(x != y);
                case 5:
                    return new BoolValue(x > y);
                case 6:
                    return new BoolValue(x >= y);
            }
        }
        else {
            throw new ExprException("At least one operand is not an integer");
        }
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        String s = switch (op) {
            case 1 -> "<";
            case 2 -> "<=";
            case 3 -> "==";
            case 4 -> "!=";
            case 5 -> ">";
            default -> ">=";
        };
        return exp1 + s + exp2;
    }

    @Override
    public Exp deep_copy() {
        return new RelationalExp(exp1.deep_copy(), exp2.deep_copy(), op);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1 = exp1.typeCheck(typeEnv);
        t2 = exp2.typeCheck(typeEnv);

        if(t1.equals(new IntType()))
            if(t2.equals((new IntType())))
                return new BoolType();
            else
                throw new MyException("second operand not an integer");
        else
            throw new MyException("first operand not an integer ");
    }
}
