package Controller;

import Model.ADT.IStack;
import Model.Exceptions.ADTException;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtException;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    ExecutorService executor;
    public Controller(IRepository repo){
        repository = repo;
    }

    public String getName(){
        String name = repository.getOriginalProgram().toString();
        return name;
    }
    public List<PrgState> removeCompletedProgram(List<PrgState> inProgramList){
        return inProgramList.stream().filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddrs, Map<Integer, Value> heap) {
        // Collects entries from the heap that have keys present in symTableAddrs
        return heap.entrySet().stream()
                .filter(e -> symTableAddrs.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        Map<Integer, Value> newHeap = new HashMap<>();

        // Iterate through the original heap and copy reachable entries to the newHeap
        for (Integer k : heap.keySet()) {
            if (symbolTableAddresses.contains(k))
                newHeap.put(k, heap.get(k));
        }

        // Collects entries from the heap that have keys present in symbolTableAddresses
        Map<Integer, Value> map = heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // Follow reference chains to include reachable objects in the newHeap
        for (Map.Entry<Integer, Value> entry : map.entrySet()) {
            Value value = entry.getValue();
            while (value instanceof RefValue refVal) {
                int address = refVal.getAddress();
                Value value2 = heap.get(address);
                newHeap.put(address, value2);
                value = value2;
            }
        }

        return newHeap;
    }

    /*
    public PrgState oneStep(PrgState state) throws MyException, ADTException, StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }*/

    public void oneStepForAllPrograms(List<PrgState> list) throws InterruptedException {
        List<Callable<PrgState>> callList = list.stream().
                map((PrgState p ) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        //invoke returns a list of Future objects
        List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                .map(future->{
                    try {
                        return future.get();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        list.addAll(newProgramList);
        list.forEach(prog -> {
            try {
                repository.logPrgStateExec(prog);
            } catch (MyException | IOException e) {
                e.printStackTrace();
            }
        });

        repository.setPrgList(list);

    }
    public void allStep() throws MyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programList = removeCompletedProgram(repository.getPrgList());

        while(!programList.isEmpty()){
            oneStepForAllPrograms(programList);

            //garbage collector
            programList.forEach(program->program.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(
                    program.getSymTable().getContent().values()), program.getHeap().getContent())));

            programList = removeCompletedProgram(repository.getPrgList());
        }

        executor.shutdown();
        repository.setPrgList(programList);
    }

    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(8);
        removeCompletedProgram(repository.getPrgList());
        List<PrgState> programStates = repository.getPrgList();
        if(!programStates.isEmpty())
        {
            try {
                oneStepForAllPrograms(repository.getPrgList());
            } catch (InterruptedException e) {
                System.out.println();
            }
            programStates.forEach(e -> {
                try {
                    repository.logPrgStateExec(e);
                } catch (IOException | MyException e1) {
                    System.out.println();
                }
            });
            removeCompletedProgram(repository.getPrgList());
            executor.shutdownNow();
        }
    }

    public IRepository getRepository(){
        return repository;
    }

}
