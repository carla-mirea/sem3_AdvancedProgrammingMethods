package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.MyIToySemaphoreTable;
import Model.ADT.Tuple;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStatement implements IStmt{
    private final String var;
    private final Exp expression1;
    private final Exp expression2;
    private static final Lock lock = new ReentrantLock();

    public NewSemaphoreStatement(String var, Exp expression1, Exp expression2) {
        this.var = var;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphoreTable();
        IntValue nr1 = (IntValue) (expression1.eval(symTable, heap));
        IntValue nr2 = (IntValue) (expression2.eval(symTable, heap));
        int number1 = nr1.getValue();
        int number2 = nr2.getValue();
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress, new Tuple<>(number1, new ArrayList<>(), number2));
        if (symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType()))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new MyException(String.format("%s in not defined in the symbol table!", var));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deep_copy() {
        return new NewSemaphoreStatement(var, expression1.deep_copy(), expression2.deep_copy());
    }

    @Override
    public String toString() {
        return String.format("newSemaphore(%s, %s, %s)", var, expression1, expression2);
    }
}
