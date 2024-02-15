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
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> stack = state.getStack();
        IDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name)){
            if(symTbl.lookup(var_name).getType() instanceof RefType){
                Value val = exp.eval(symTbl, heap);
                Value tblVal = symTbl.lookup(var_name);
                if(val.getType().equals(((RefType)(tblVal.getType())).getInner())){
                    int addr = heap.allocate(val);
                    symTbl.update(var_name, new RefValue(addr, val.getType()));
                }
                else{
                    throw new StmtException("Value's type is not correct!");
                }
            }
            else{
                throw new StmtException("Value's type is not reference!");
            }
        }
        else{
            throw new StmtException("Value is not declared!");
        }
        state.setSymTable(symTbl);
        state.setHeap(heap);
        state.setExeStack(stack);
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
