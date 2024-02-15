package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.Expression.NotExpression;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;

public class RepeatUntilStatement implements IStmt{
    private final IStmt statement;
    private final Exp expression;

    public RepeatUntilStatement(IStmt statement, Exp expression) {
        this.statement = statement;
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, StmtException, ExprException {
        IStack<IStmt> exeStack = state.getStack();
        IStmt converted = new CompStmt(statement, new WhileStmt(new NotExpression(expression), statement));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typeCheck(typeEnv);
        if (type.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deep_copy());
            return typeEnv;
        } else {
            throw new MyException("Expression in the repeat statement must be of Bool type!");
        }
    }

    @Override
    public IStmt deep_copy() {
        return new RepeatUntilStatement(statement.deep_copy(), expression.deep_copy());
    }

    @Override
    public String toString() {
        return String.format("repeat(%s) until (%s)", statement, expression);
    }
}
