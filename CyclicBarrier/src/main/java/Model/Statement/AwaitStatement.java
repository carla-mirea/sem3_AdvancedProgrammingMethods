package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.MyIBarrierTable;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStmt{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public AwaitStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        if (symTable.isDefined(var)) {
            IntValue f = (IntValue) symTable.lookup(var);
            int foundIndex = f.getValue();
            if (barrierTable.containsKey(foundIndex)) {
                Pair<Integer, List<Integer>> foundBarrier = barrierTable.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                ArrayList<Integer> list = (ArrayList<Integer>) foundBarrier.getValue();
                if (N1 > NL) {
                    if (list.contains(state.getId()))
                        state.getStack().push(this);
                    else {
                        list.add(state.getId());
                        barrierTable.update(foundIndex, new Pair<>(N1, list));
                        state.setBarrierTable(barrierTable);
                    }
                }
            } else {
                throw new StmtException("Index not in Barrier Table!");
            }
        } else {
            throw new StmtException("Var is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var is not of type int!");
    }

    @Override
    public IStmt deep_copy() {
        return new AwaitStatement(var);
    }

    @Override
    public String toString() {
        return String.format("await(%s)", var);
    }
}
