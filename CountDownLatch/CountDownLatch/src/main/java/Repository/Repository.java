package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> states;
    IStmt originalProgram;
    private String logFilePath;

    public Repository(PrgState prgState){
        this.originalProgram = prgState.getOriginalProgram();
        states = new LinkedList<>();
    }

    public Repository(PrgState prgState, String fileName) throws IOException, MyException {
        this.originalProgram = prgState.getOriginalProgram();
        this.logFilePath = fileName;
        File yourFile = new File(fileName);
        yourFile.createNewFile();
        try (FileWriter fileWriter = new FileWriter(yourFile)) {
            fileWriter.write("");
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        states = new LinkedList<>();
    }

    public Repository(){
        states = new LinkedList<>();
    }
    @Override
    public List<PrgState> getPrgList() {
        return states;
    }

    /*
    @Override
    public PrgState getCrtPrg() {
        PrgState state = states.get(0);
        states.remove(0);
        return state;
    } */

    @Override
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public void addState(PrgState state) {
            states.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException {
        File yourFile = new File(logFilePath);
        yourFile.createNewFile();
        try (FileWriter fileWriter = new FileWriter(yourFile, true)) {
            fileWriter.write(prgState + "\n");
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        states = list;
    }

    @Override
    public int getSize() {
        return states.size();
    }
}
