package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Type.Type;

public class SleepStatement implements IStmt{
    private int number;

    public SleepStatement(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, StmtException, ExprException {
        if (number != 0) {
            IStack<IStmt> stack = state.getStack();
            stack.push(new SleepStatement(number - 1));
        }
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new SleepStatement(number);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}