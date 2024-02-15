package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op;

    public LogicExp(Exp exp1, Exp exp2, int operand){
        this.e1 = exp1;
        this.e2 = exp2;
        op = operand;
    }
    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val1, val2;
        val1=e1.eval(tbl,heap);
        if (val1.getType().equals(new BoolType())) {
            val2 = e2.eval(tbl,heap);
            if (val2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue)val1;
                BoolValue i2 = (BoolValue)val2;
                boolean x = i1.getValue();
                boolean y = i2.getValue();
                if (op == 1){
                    return new BoolValue(x && y);
                }
                else if (op == 2) {
                    return new BoolValue(x || y);
                }
                else
                    throw  new ExprException("Incorrect operand");
            }
            else {
                throw new ExprException("Second operand is not a boolean");
            }
        }
        else {
            throw new ExprException("First operand is not a boolean");
        }

    }

    @Override
    public Exp deep_copy() {
        return new LogicExp(e1.deep_copy(), e2.deep_copy(), op);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);

        if(t1.equals(new BoolType()))
            if(t2.equals((new BoolType())))
                return new BoolType();
            else
                throw new MyException("second operand not an boolean");
        else
            throw new MyException("first operand not an boolean");
    }


    public String toString(){
        return switch (op) {
            case 1 -> e1.toString() + "&&" + e2.toString();
            case 2 -> e1.toString() + "||" + e2.toString();
            default -> "";
        };
    }
}
