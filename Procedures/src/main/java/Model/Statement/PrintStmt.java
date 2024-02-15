package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IList;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp e){
        exp=e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getStack();
        IList<Value> outConsole = state.getOutConsole();
        outConsole.add(exp.eval(state.getSymTable().peek(), state.getHeap()));
        state.setExeStack(stk);
        state.setOutConsole(outConsole);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new PrintStmt(exp.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    public String toString(){
        return "print(" + exp.toString() +")";
    }
}
