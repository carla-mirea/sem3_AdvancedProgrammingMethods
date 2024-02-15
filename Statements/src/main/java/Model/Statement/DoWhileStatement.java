package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;

public class DoWhileStatement implements IStmt{
    private final IStmt statement;
    private final Exp expression;

    public DoWhileStatement(Exp expression, IStmt statement) {
        this.statement = statement;
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, StmtException, ExprException {
        IStmt converted = new CompStmt(statement, new WhileStmt(expression, statement));
        state.getStack().push(converted);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typeCheck(typeEnv);
        if (typeExpression.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deep_copy());
            return typeEnv;
        } else
            throw new MyException("Condition in the do while statement must be bool!");
    }

    @Override
    public IStmt deep_copy() {
        return new DoWhileStatement(expression.deep_copy(), statement.deep_copy());
    }

    @Override
    public String toString() {
        return String.format("do {%s} while (%s)", statement, expression);
    }
}
