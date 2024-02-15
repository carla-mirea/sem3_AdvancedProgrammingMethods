package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.Expression.RelationalExp;
import Model.PrgState;
import Model.Type.Type;

public class SwitchStatement implements IStmt{
    private Exp exp;
    private Exp exp1;
    private IStmt stmt1;
    private Exp exp2;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStatement(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public String toString() {
        return "Switch(" + exp +
                ") (case " + exp1 +
                ": " + stmt1 +
                ") (case " + exp2 +
                ": " + stmt2 +
                ") (default: " + stmt3 +
                ')';
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        IStmt converted = new IfStmt(new RelationalExp(exp, exp1, 3), stmt1,
                new IfStmt(new RelationalExp(exp, exp2, 3), stmt2, stmt3));
        stack.push(converted);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new SwitchStatement(exp.deep_copy(), exp1.deep_copy(), stmt1.deep_copy(), exp2.deep_copy(), stmt2.deep_copy(), stmt3.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typeCheck(typeEnv);
        Type type1 = exp1.typeCheck(typeEnv);
        Type type2 = exp2.typeCheck(typeEnv);
        if (type.equals(type1) && type.equals(type2)) {
            stmt1.typeCheck(typeEnv.deep_copy());
            stmt2.typeCheck(typeEnv.deep_copy());
            stmt3.typeCheck(typeEnv.deep_copy());
            return typeEnv;
        }
        else {
            throw new MyException("The expression types do not match in the switch statement!");
        }
    }
}