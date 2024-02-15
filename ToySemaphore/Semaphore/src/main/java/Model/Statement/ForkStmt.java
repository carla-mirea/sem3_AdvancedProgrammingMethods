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
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);
        IDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry: state.getSymTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().deep_copy());
        }

        return new PrgState(newStack, newSymTable, state.getOutConsole(), state.getFileTable(), state.getHeap(), state.getToySemaphoreTable());
    }

    @Override
    public IStmt deep_copy() {
        return new ForkStmt(statement.deep_copy());
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
