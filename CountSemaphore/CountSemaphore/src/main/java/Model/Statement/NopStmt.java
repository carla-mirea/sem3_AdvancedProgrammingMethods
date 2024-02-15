package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.Type;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new NopStmt();
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
