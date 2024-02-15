package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;


public class WriteHeapStmt implements IStmt {
    String var_name;
    Exp exp;

    public WriteHeapStmt(String variable, Exp expression ){
        var_name = variable;
        exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)){
            if (symTable.lookup(var_name).getType() instanceof RefType){
                RefValue ref_value = (RefValue) symTable.lookup(var_name);
                if (heap.contains(ref_value.getAddress())){
                    Value val = exp.eval(symTable, heap);
                    if (symTable.lookup(var_name).getType().equals(new RefType(val.getType()))) {
                        int address = ref_value.getAddress();
                        heap.update(address, val);
                    }
                    else {
                        throw new StmtException("The pointing variable has a different type than the evaluated expression.");
                    }
                }
                else{
                    throw new StmtException("The address to which " + var_name + " points is not in the heap");
                }
            }
            else {
                throw new StmtException(var_name + "is not a reference variable");
            }
        }
        else {
            throw new StmtException( var_name + "is not defined");
        }

        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new WriteHeapStmt(var_name, exp.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typeExp = exp.typeCheck(typeEnv);

        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("Heap Writing Statement: right side and left side have different types\n");
    }

    public String toString() {
        return "wH(" + var_name + "," + exp + ")";
    }
}
