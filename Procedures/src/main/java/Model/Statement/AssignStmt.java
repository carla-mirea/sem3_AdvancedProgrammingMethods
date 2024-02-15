package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String s, Exp e){
        id = s;
        exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IDictionary<String, Value>> symTable = state.getSymTable();
        IDictionary<String, Value> currentSymbolTable = symTable.peek();

        //Value value = exp.eval(symTable, state.getHeap());
        if (currentSymbolTable.isDefined(id)) {
            Value value = exp.eval(currentSymbolTable, state.getHeap());
            Type type = (currentSymbolTable.lookup(id)).getType();
            if (value.getType().equals(type)) {
                currentSymbolTable.update(id, value);
            }
            else {
                throw new StmtException("Declared type of variable " +
                        id +
                        " and type of the assigned expression do not match");
            }
        }
        else {
            throw new StmtException("The used variable " + id + " was not declared before");
        }
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new AssignStmt(id, exp.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp= exp.typeCheck(typeEnv);

        if(typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right side and left side have different types\n");
    }

    public String toString(){
        return id+"="+ exp.toString();
    }

}
