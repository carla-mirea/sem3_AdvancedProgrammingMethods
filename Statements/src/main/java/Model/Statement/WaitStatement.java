package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.ValueExp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.IntValue;

public class WaitStatement implements IStmt{
    private final int value;

    public WaitStatement(int value) {
        this.value = value;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        if (value != 0) {
            IStack<IStmt> exeStack = state.getStack();
            exeStack.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(value))),
                    new WaitStatement(value - 1)));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deep_copy() {
        return new WaitStatement(value);
    }

    @Override
    public String toString() {
        return String.format("wait(%s)", value);
    }
}
