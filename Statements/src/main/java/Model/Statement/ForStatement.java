package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.Expression.RelationalExp;
import Model.Expression.VarExp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;

public class ForStatement implements IStmt{
    private final String variable;
    private final Exp expression1;
    private final Exp expression2;
    private final Exp expression3;
    private final IStmt statement;

    public ForStatement(String variable, Exp expression1, Exp expression2, Exp expression3, IStmt statement) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        IStack<IStmt> exeStack = state.getStack();
        IStmt converted = new CompStmt(new AssignStmt("v", expression1),
                new WhileStmt(new RelationalExp(new VarExp("v"), expression2, 1),
                        new CompStmt(statement, new AssignStmt("v", expression3))));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);
        Type type3 = expression3.typeCheck(typeEnv);

        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("The for statement is invalid!");
    }

    @Override
    public IStmt deep_copy() {
        return new ForStatement(variable, expression1.deep_copy(), expression2.deep_copy(), expression3.deep_copy(), statement.deep_copy());
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", variable, expression1, variable, expression2, variable, expression3, statement);
    }
}
