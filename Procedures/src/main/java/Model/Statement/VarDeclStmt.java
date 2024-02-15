package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public  VarDeclStmt(String n, Type t){
        name = n;
        type = t;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IDictionary<String, Value>> table = state.getSymTable();
        IDictionary<String, Value> currentSymbolTable = table.peek();
        if (currentSymbolTable.isDefined(name)) {
            throw new StmtException("Variable is already declared");
        } else {
            /*if (type.equals(new IntType())) {
                table.add(name, type.defaultValue());
            }else if (type.equals(new BoolType())) {
                table.add(name, type.defaultValue());
            }else if (type.equals(new StringType())) {
                table.add(name, type.defaultValue());
            }
            else {
                throw new StmtException("Type does not exist");
            } */
            currentSymbolTable.add(name, type.defaultValue());
        }
        state.setSymTable(table);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new VarDeclStmt(name, type.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name,type);
        return typeEnv;
    }

    public String toString() {
        return type + " " + name;
    }
}
