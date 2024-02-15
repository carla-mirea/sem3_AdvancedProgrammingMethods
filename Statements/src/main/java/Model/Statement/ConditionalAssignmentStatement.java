package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.Expression.VarExp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;

public class ConditionalAssignmentStatement implements IStmt {
    private String v;

    private Exp exp1;
    private Exp exp2;
    private Exp exp3;



    public ConditionalAssignmentStatement(String v, Exp exp1, Exp exp2, Exp exp3) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }



    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        IStack<IStmt> exeStack = state.getStack();
        // we create a new if statement which has as condition exp1 and assigns to v exp2 if exp1 is true, and exp3 otherwise
        IStmt converted = new IfStmt(exp1, new AssignStmt(v, exp2), new AssignStmt(v, exp3));
        // and we push the statement on the stack
        exeStack.push(converted);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new ConditionalAssignmentStatement(v, exp1.deep_copy(), exp2.deep_copy(), exp3.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        // we evaluate the variable v and the expressions exp1, exp2, exp3
        Type typev = new VarExp(v).typeCheck(typeEnv);
        Type type1 = exp1.typeCheck(typeEnv);
        Type type2 = exp2.typeCheck(typeEnv);
        Type type3 = exp3.typeCheck(typeEnv);
        // exp1 must be of type bool and exp2 and exp3 must have the same type as v
        if (type1.equals(new BoolType()) && type2.equals(typev) && type3.equals(typev))
            return typeEnv;
        else
            throw new MyException("The conditional assignment is invalid!");    }


    @Override
    public String toString() {
        return "ConditionalAssignment{" +
                "v='" + v + '\'' +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", exp3=" + exp3 +
                '}';
    }
}
