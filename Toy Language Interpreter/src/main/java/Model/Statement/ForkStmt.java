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
        IDictionary<String, Value> symbolTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        IList<Value> out = state.getOutConsole();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IDictionary<String, Value> newSymTable = new MyDictionary<>();

        for(Map.Entry<String, Value> entry: symbolTable.getContent().entrySet()){
            newSymTable.add(entry.getKey(), entry.getValue().deep_copy());
        }

        IStack<IStmt> newStack = new MyStack<>();
        PrgState newState = new PrgState(newStack, newSymTable, out, fileTable, heap, statement);

        return newState;
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
