package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.Type;

public interface IStmt {
    public PrgState execute(PrgState state) throws MyException;
    public IStmt deep_copy();
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException;
}
