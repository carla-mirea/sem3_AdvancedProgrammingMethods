package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Statement.IfStmt;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public List<PrgState> getPrgList();

    // PrgState getCrtPrg();
    IStmt getOriginalProgram();
    void addState(PrgState state);
    void logPrgStateExec(PrgState state) throws MyException, IOException;

    void setPrgList(List<PrgState> list);

    int getSize();
}
