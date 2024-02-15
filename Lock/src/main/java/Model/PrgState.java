package Model;

import Model.ADT.*;
import Model.Exceptions.MyException;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    IStack<IStmt> exeStack;
    IDictionary<String, Value> symTable;
    IList<Value> out;
    IDictionary<StringValue, BufferedReader> fileTable;
    IStmt originalProgram;
    IHeap<Value> heap;
    private static int id = 0;
    private int currentId;
    MyILockTable lockTable;

    public PrgState(IStack<IStmt> stk, IDictionary<String, Value> symtbl, IList<Value> ot, IDictionary<StringValue, BufferedReader> table, IHeap<Value> givenHeap, MyILockTable lockTbl, IStmt prg) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = table;
        heap = givenHeap;
        originalProgram = prg.deep_copy();
        stk.push(prg);
        currentId = incId();
        lockTable = lockTbl;
    }

    public PrgState(IStack<IStmt> stk, IDictionary<String, Value> symtbl, IList<Value> ot, IDictionary<StringValue, BufferedReader> table, IHeap<Value> givenHeap, MyILockTable lockTbl) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = table;
        heap = givenHeap;
        currentId = incId();
        lockTable = lockTbl;
    }

    public static synchronized int incId(){
        id++;
        return id-1;
    }

    public int getId(){
        return currentId;
    }

    public IStack<IStmt> getStack() {
        return exeStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public IStmt getOriginalProgram(){
        return originalProgram;
    }

    public IHeap<Value> getHeap() { return heap; }

    @Override
    public String toString() {
        String str = "Program state\n" +
                "Id: " + currentId + "\n" +
                "Exe Stack: " + exeStack + " \n" +
                "Sym Table: " + symTable + " \n" +
                "Output Console: " + out + " \n" +
                "Heap: " + heap + " \n" +
                "File table: " + fileTable + " \n" +
                "Lock Table: " + lockTable + "\n";
        return str;
    }

    public void setExeStack(IStack<IStmt> stack) {
        exeStack = stack;
    }

    public void setSymTable(IDictionary<String, Value> table) {
        symTable = table;
    }

    public IList<Value> getOutConsole() {
        return out;
    }

    public void setOutConsole(IList<Value> outConsole) {
        out = outConsole;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> table) { fileTable=table; }

    public IDictionary<StringValue, BufferedReader> getFileTable() { return fileTable;}

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public void setLockTable(MyILockTable newLockTable) {
        this.lockTable = newLockTable;
    }

    public MyILockTable getLockTable() {
        return lockTable;
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException{
        if (exeStack.isEmpty())
            return null;
            //throw new MyException("Program Stack is empty!");

        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }
}
