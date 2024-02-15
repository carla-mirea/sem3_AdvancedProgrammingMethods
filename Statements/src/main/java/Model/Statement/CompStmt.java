package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt second;
    public String toString(){
        return "(" + first + ";" + second + ")" ;
    }

    public CompStmt(IStmt f, IStmt s){
        first =f;
        second = s;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> stk = state.getStack();
        stk.push(second);
        stk.push(first);
        state.setExeStack(stk);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new CompStmt(first.deep_copy(), second.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}
