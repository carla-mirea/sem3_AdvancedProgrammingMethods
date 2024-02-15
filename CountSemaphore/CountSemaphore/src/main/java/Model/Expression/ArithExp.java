package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-start, 4-divide

    public ArithExp(char operand, Exp exp1, Exp exp2){
        this.e1 = exp1;
        this.e2 = exp2;
        if (operand == '+')
            this.op = 1;
        else if (operand == '-')
            this.op = 2;
        else if (operand == '*')
            this.op = 3;
        else if (operand == '/')
            this.op = 4;
    }
    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val1, val2;
        val1 = e1.eval(tbl, heap);
        if (val1.getType().equals(new IntType())){
            val2=e2.eval(tbl, heap);
            if (val2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)val1;
                IntValue i2 = (IntValue)val2;
                int n1 = i1.getValue();
                int n2 = i2.getValue();
                switch (op) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if (n2 == 0)
                            throw new ExprException("Division by 0!");
                        else
                            return new IntValue(n1 / n2);
                    default:
                        throw new ExprException("Incorrect operand!");
                }
            }
            else {
                throw new ExprException("Second operand is not an integer!");
            }
        }
        else
            throw new ExprException("First operand is not an integer!");
    }

    @Override
    public Exp deep_copy() {
        char new_op;
        if (op == 1)
            new_op = '+';
        else if (op == 2)
            new_op = '-';
        else if (op == 3)
            new_op = '*';
        else
            new_op ='/';
        return new ArithExp(new_op, e1.deep_copy(), e2.deep_copy());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);
        if(t1.equals(new IntType()))
            if(t2.equals((new IntType())))
                return new IntType();
            else
                throw new MyException("second operand not an integer");
        else
            throw new MyException("first operand not an integer ");
    }

    public String toString() {
        return switch (op) {
            case 1 -> e1.toString() + "+" + e2.toString();
            case 2 -> e1.toString() + "-" + e2.toString();
            case 3 -> e1.toString() + "*" + e2.toString();
            case 4 -> e1.toString() + '/' + e2.toString();
            default -> "";
        };
    }

}
