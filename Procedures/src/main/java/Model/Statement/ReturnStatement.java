package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Type.Type;

public class ReturnStatement implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTable().pop();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deep_copy() {
        return new ReturnStatement();
    }

    @Override
    public String toString() {
        return "return";
    }
}
