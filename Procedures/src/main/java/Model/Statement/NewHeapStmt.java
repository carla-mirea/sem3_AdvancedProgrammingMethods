package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.IStack;
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

public class NewHeapStmt implements IStmt{
    String var_name;
    Exp exp;

    public NewHeapStmt(String var_name, Exp exp){
        this.var_name = var_name;
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IDictionary<String, Value>> symTable = state.getSymTable();
        IDictionary<String, Value> currentSymbolTable = symTable.peek();

        IHeap heap = state.getHeap();
        if (currentSymbolTable.isDefined(var_name)) {
            Value varValue = currentSymbolTable.lookup(var_name);
            if ((varValue.getType() instanceof RefType)) {
                Value evaluated = exp.eval(currentSymbolTable, heap);
                //Value tblVal = currentSymbolTable.lookup(var_name);
                Type locationType = ((RefValue) varValue).getLocationType();
                if (locationType.equals(evaluated.getType())) {
                    int newPosition = heap.allocate(evaluated);
                    currentSymbolTable.put(var_name, new RefValue(newPosition, locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                } else
                    throw new MyException(String.format("%s not of %s", var_name, evaluated.getType()));
            } else {
                throw new MyException(String.format("%s in not of RefType", var_name));
            }
        } else {
            throw new MyException(String.format("%s not in symTable", var_name));
        }
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new NewHeapStmt(var_name, exp.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typeExp = exp.typeCheck(typeEnv);

        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("New Heap Statement: right side and left side have different types\n");
    }

    public String toString(){
        return "new(" + var_name + ", " + exp + ")";
    }
}
