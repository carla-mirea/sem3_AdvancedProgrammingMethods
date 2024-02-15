package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.MyDictionary;
import Model.ADT.MyIProcTable;
import Model.Exceptions.MyException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

import java.util.List;

public class CallProcStatement implements IStmt{
    private final String procedureName;
    private final List<Exp> expressions;

    public CallProcStatement(String procedureName, List<Exp> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTable = state.getTopSymTable();
        IHeap heap = state.getHeap();
        MyIProcTable procTable = state.getProcTable();
        if (procTable.isDefined(procedureName)) {
            List<String> variables = procTable.lookUp(procedureName).getKey();
            IStmt statement = procTable.lookUp(procedureName).getValue();
            IDictionary<String, Value> newSymTable = new MyDictionary<>();
            for(String var: variables) {
                int ind = variables.indexOf(var);
                newSymTable.put(var, expressions.get(ind).eval(symTable, heap));
            }
            state.getSymTable().push(newSymTable);
            state.getStack().push(new ReturnStatement());
            state.getStack().push(statement);
        } else {
            throw new MyException("Procedure not defined!");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deep_copy() {
        return new CallProcStatement(procedureName, expressions);
    }

    @Override
    public String toString() {
        return String.format("call %s%s", procedureName, expressions.toString());
    }
}