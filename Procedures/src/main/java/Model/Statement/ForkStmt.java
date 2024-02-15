package Model.Statement;

import Model.ADT.*;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.Map;


public class ForkStmt implements IStmt{
    IStmt statement;
    public ForkStmt(IStmt stmt){
        this.statement = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);
        IStack<IDictionary<String, Value>> newSymTable = state.getSymTable().clone();
        return new PrgState(newStack, newSymTable, state.getOutConsole(), state.getFileTable(), state.getHeap(), state.getProcTable());
    }

    @Override
    public IStmt deep_copy() {
        return new ForkStmt(statement);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String,Type> clone = new MyDictionary<String, Type>();
        clone.setContent(typeEnv.getContent());

        statement.typeCheck(clone);

        return typeEnv;
    }

    public String toString(){
        return "fork( " + statement.toString() + ") ";
    }
}
