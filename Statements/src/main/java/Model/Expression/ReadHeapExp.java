package Model.Expression;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements Exp {
    private Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap<Value> heap) throws ExprException {
        Value val = exp.eval(symTable, heap);
        if (val instanceof RefValue refVal) {
            if (heap.contains(refVal.getAddress())) {
                return heap.get(refVal.getAddress());
            } else {
                throw new ExprException("The address doesn't exist in the heap");
            }

        } else {
            throw new ExprException("The expression could not be evaluated to a RefValue");
        }
    }

    public Exp deep_copy() {
        return new ReadHeapExp(exp.deep_copy());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type t = exp.typeCheck(typeEnv);

        if(t instanceof RefType)
        {
            RefType reft = (RefType) t;
            return reft.getInner();
        }
        else
            throw new MyException("the rH arg is not a Reference Type");
    }


    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
