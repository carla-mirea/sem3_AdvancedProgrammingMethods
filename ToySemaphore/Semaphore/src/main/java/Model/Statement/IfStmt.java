package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.ADT.MyDictionary;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenSt;
    IStmt elseSt;

    public IfStmt(Exp e, IStmt then, IStmt elses) {
        exp = e;
        thenSt = then;
        elseSt = elses;
    }

    public String toString() {
        return "if (" + exp + ") then {" + thenSt+ "} else {" + elseSt+ "}";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> stack = state.getStack();
        Value cond = exp.eval(state.getSymTable(), state.getHeap());
        if (!cond.getType().equals(new BoolType())) {
            throw new StmtException("Condition is not of boolean");
        }
        if (cond.equals(new BoolValue(true))) {
            stack.push(thenSt);
        } else {
            stack.push(elseSt);
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new IfStmt(exp.deep_copy(), thenSt.deep_copy(), elseSt.deep_copy());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);

        if(typeExp.equals(new BoolType()))
        {
            IDictionary<String,Type> clone = new MyDictionary<>();
            clone.setContent(typeEnv.getContent());
            thenSt.typeCheck(clone);
            elseSt.typeCheck(clone);

            return typeEnv;
        }
        else
            throw new MyException("The condition of IF does not have bool type\n");
    }
}
