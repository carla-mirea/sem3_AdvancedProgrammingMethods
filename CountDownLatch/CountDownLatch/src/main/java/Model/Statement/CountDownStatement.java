package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.MyILatchTable;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.ValueExp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStatement implements IStmt{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public CountDownStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, StmtException, ExprException {
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        MyILatchTable latchTable = state.getLatchTable();
        if (symTable.isDefined(var)) {
            IntValue fi = (IntValue) symTable.lookup(var);
            int foundIndex = fi.getValue();
            if (latchTable.containsKey(foundIndex)) {
                if (latchTable.get(foundIndex) > 0) {
                    latchTable.update(foundIndex, latchTable.get(foundIndex) - 1);
                }
                state.getStack().push(new PrintStmt(new ValueExp(new IntValue(state.getId()))));
            } else {
                throw new StmtException("Index not found in the latch table!");
            }
        } else {
            throw new StmtException("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException(String.format("%s is not of int type!", var));
    }

    @Override
    public IStmt deep_copy() {
        return new CountDownStatement(var);
    }

    @Override
    public String toString() {
        return String.format("countDown(%s)", var);
    }
}
