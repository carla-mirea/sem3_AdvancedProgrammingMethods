package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class readFile implements IStmt{
    Exp exp;
    String var_name;

    public readFile(Exp e, String variable_name){
        exp = e;
        var_name = variable_name;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(var_name)) {
            if (symTable.lookup(var_name).getType().equals(new IntType())) {
                Value val = exp.eval(symTable, state.getHeap());
                if (val.getType().equals(new StringType())) {
                    StringValue stringVal = (StringValue) val;
                    if (fileTable.isDefined(stringVal)) {
                        BufferedReader bufferedReader = fileTable.lookup(stringVal);
                        try {
                            String line = bufferedReader.readLine();
                            Value intVal;
                            IntType type = new IntType();
                            if (Objects.equals(line, "")) {
                                intVal = type.defaultValue();
                            } else {
                                intVal = new IntValue(Integer.parseInt(line));
                            }
                            symTable.update(var_name, intVal);
                        } catch (IOException e) {
                            throw new StmtException(e.getMessage());
                        }
                    }
                    else {
                        throw new StmtException("The file " + stringVal.getValue() + " is not in the File Table!");
                    }
                }
                else {
                    throw new StmtException("The value couldn't be evaluated to a string value!");
                }
            }
            else {
                throw new StmtException(var_name + " is not of type int!");
            }
        }
        else {
            throw new StmtException(var_name + " is not defined in Sym Table");
        }

        return null;
    }

    @Override
    public IStmt deep_copy() {
        return new readFile(exp.deep_copy(), var_name);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);

        if(typeExp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("FileName not a string");
    }

    public String toString() {
        return "Readfile: (" + this.exp.toString() + ") ";
    }
}
