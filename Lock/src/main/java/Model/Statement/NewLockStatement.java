package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.MyILockTable;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.scene.Parent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStmt{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public NewLockStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, StmtException, ExprException {
        lock.lock();
        MyILockTable lockTable = state.getLockTable();
        IDictionary<String, Value> symTable = state.getSymTable();
        int freeAddress = lockTable.getFreeValue();
        lockTable.put(freeAddress, -1);
        if (symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType()))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new StmtException("Variable not declared!");
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var is not of int type!");
    }

    @Override
    public IStmt deep_copy() {
        return new NewLockStatement(var);
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", var);
    }
}
