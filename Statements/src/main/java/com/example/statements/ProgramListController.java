package com.example.statements;

import Controller.Controller;
import Model.ADT.*;
import Model.Exceptions.ExprException;
import Model.Exceptions.MyException;
import Model.Expression.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramListController {

    public ProgramListController() {

    }
    private List<Controller> programs = new ArrayList<>();

    @FXML
    private ListView<String> programListView = new ListView<>();

    public void setListView() throws MyException, IOException {
        IStack<IStmt> stack1 = new MyStack<>();
        IStack<IStmt> stack2 = new MyStack<>();
        IStack<IStmt> stack3 = new MyStack<>();
        IStack<IStmt> stack4 = new MyStack<>();
        IStack<IStmt> stack5 = new MyStack<>();
        IStack<IStmt> stack6 = new MyStack<>();
        IStack<IStmt> stack7 = new MyStack<>();
        IStack<IStmt> stack8 = new MyStack<>();

        IStack<IStmt> stack9 = new MyStack<>();
        IStack<IStmt> stack10 = new MyStack<>();
        IStack<IStmt> stack11 = new MyStack<>();
        IStack<IStmt> stack12 = new MyStack<>();
        IStack<IStmt> stack13 = new MyStack<>();
        IStack<IStmt> stack14 = new MyStack<>();
        IStack<IStmt> stack15 = new MyStack<>();
        IStack<IStmt> stack16 = new MyStack<>();

        try {
            IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                    new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
            ex1.typeCheck(new MyDictionary<String, Type>());
            PrgState prg1 = new PrgState(stack1, new MyDictionary<String, Value>(),
                    new MyList<Value>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller controller1 = new Controller(repo1);
            repo1.addState(prg1);

            programs.add(controller1);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 1 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                    new CompStmt(new VarDeclStmt("b", new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                    new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                    new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"),
                                            new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
            ex2.typeCheck(new MyDictionary<String, Type>());
            PrgState prg2 = new PrgState(stack2, new MyDictionary<String, Value>(), new MyList<Value>(),
                    new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller controller2 = new Controller(repo2);
            repo2.addState(prg2);
            programs.add(controller2);
        }
        catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 2 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            IStmt ex3 = new CompStmt(
                    new VarDeclStmt("a", new IntType()),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(
                                    new AssignStmt("a", new ValueExp(new IntValue(2))),
                                    new CompStmt(
                                            new IfStmt(
                                                    new RelationalExp(new VarExp("a"), new ValueExp(new IntValue(3)), 1),
                                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                    new AssignStmt("v", new ValueExp(new IntValue(3)))
                                            ),
                                            new PrintStmt(new VarExp("v"))))));
            ex3.typeCheck(new MyDictionary<String, Type>());
            PrgState prg3 = new PrgState(stack3, new MyDictionary<String, Value>(), new MyList<Value>(),
                    new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller controller3 = new Controller(repo3);
            repo3.addState(prg3);
            programs.add(controller3);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 3 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            // EXAMPLE WITH FILE -from test
            IStmt ex4 = new CompStmt(
                    new VarDeclStmt("fileName", new StringType()),
                    new CompStmt(new AssignStmt("fileName", new ValueExp(new StringValue("test.txt"))),
                            new CompStmt(new openRFile(new VarExp("fileName")),
                                    new CompStmt(new VarDeclStmt("x", new IntType()),
                                            new CompStmt(new readFile(new VarExp("fileName"), "x"),
                                                    new CompStmt(new PrintStmt(new VarExp("x")),
                                                            new CompStmt(new readFile(new VarExp("fileName"), "x"),
                                                                    new CompStmt(new PrintStmt(new VarExp("x")),
                                                                            new closeRFile(new VarExp("fileName"))))))))));
            ex4.typeCheck(new MyDictionary<String, Type>());
            PrgState prg4 = new PrgState(stack4, new MyDictionary<String, Value>(), new MyList<Value>(),
                    new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller controller4 = new Controller(repo4);
            repo4.addState(prg4);
            programs.add(controller4);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 4 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            //GARBAGE COLLECTOR EXAMPLE
            IStmt ex5 = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                            new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(30))),
                                                    new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                            )
                                    )
                            ))
            );
            ex5.typeCheck(new MyDictionary<String, Type>());

            PrgState prg5 = new PrgState(stack5, new MyDictionary<>(), new MyList<>(),
                    new MyDictionary<>(), new MyHeap<>(), ex5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller controller5 = new Controller(repo5);
            repo5.addState(prg5);
            programs.add(controller5);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 5 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            //while example
            IStmt ex6 = new CompStmt(new VarDeclStmt("x", new IntType()),
                    new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(2))),
                            new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), 5),
                                    new CompStmt(new PrintStmt(new VarExp("x")),
                                            new AssignStmt("x", new ArithExp('-', new VarExp("x"), new ValueExp(new IntValue(1)))))),
                                    new PrintStmt(new VarExp("x")))));
            ex6.typeCheck(new MyDictionary<String, Type>());


            PrgState prg6 = new PrgState(stack6, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>(), ex6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller controller6 = new Controller(repo6);
            repo6.addState(prg6);
            programs.add(controller6);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 6 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            // ex with write heap
            IStmt ex7 = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                    new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                            new PrintStmt(new ReadHeapExp(new VarExp("v"))))

                            ))
            );

            ex7.typeCheck(new MyDictionary<String, Type>());

            PrgState prg7 = new PrgState(stack7, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller controller7 = new Controller(repo7);
            repo7.addState(prg7);
            programs.add(controller7);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 7 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        try {
            IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                    new CompStmt(new NewHeapStmt("a", new ValueExp(new IntValue(22))),
                                            new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                                    new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                                            new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
            ex8.typeCheck(new MyDictionary<String, Type>());
            PrgState prg8 = new PrgState(stack8, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex8);
            IRepository repo8 = new Repository(prg8, "log8.txt");
            Controller controller8 = new Controller(repo8);
            repo8.addState(prg8);
            programs.add(controller8);
        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 8 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        // sleep
        try {
            IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                            new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), 1),
                                    new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp('+', new VarExp("v"),
                                                    new ValueExp(new IntValue(1)))))),
                                            new AssignStmt("v", new ArithExp('+', new VarExp("v"),
                                                    new ValueExp(new IntValue(1))))
                                            )),
                                    new CompStmt(new SleepStatement(5), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))
                            ) ));

            ex9.typeCheck(new MyDictionary<String, Type>());
            PrgState prg9 = new PrgState(stack9, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex9);
            IRepository repo9 = new Repository(prg9, "log9.txt");
            Controller controller9 = new Controller(repo9);
            repo9.addState(prg9);
            programs.add(controller9);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 9 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        // alt ex sleep
        /*
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))),
                                new CompStmt(new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("v"))))),
                                new CompStmt(new Sleep(10), new PrintStmt(new ArithExp("*", new VarExp("v"), new ValueExp(new IntValue(10))))))));
         */


        // switch statement
        try {
            IStmt ex10 = new CompStmt(new VarDeclStmt("a", new IntType()),
                    new CompStmt(new VarDeclStmt("b", new IntType()),
                            new CompStmt(new VarDeclStmt("c", new IntType()),
                                    new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                            new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                    new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                            new CompStmt(new SwitchStatement(
                                                                    new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                    new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                    new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                    new ValueExp(new IntValue(10)),
                                                                    new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                    new PrintStmt(new ValueExp(new IntValue(300)))
                                                            ), new PrintStmt(new ValueExp(new IntValue(300))))))))));

            ex10.typeCheck(new MyDictionary<String, Type>());
            PrgState prg10 = new PrgState(stack10, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex10);
            IRepository repo10 = new Repository(prg10, "log10.txt");
            Controller controller10 = new Controller(repo10);
            repo10.addState(prg10);
            programs.add(controller10);

        }catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 10 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        // conditional assignment statement
        try {
            IStmt ex11 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                    new CompStmt(new VarDeclStmt("b", new RefType(new IntType())),
                            new CompStmt(new VarDeclStmt("v", new IntType()),
                                    new CompStmt(new NewHeapStmt("a", new ValueExp(new IntValue(0))),
                                            new CompStmt(new NewHeapStmt("b", new ValueExp(new IntValue(0))),
                                                    new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(1))),
                                                            new CompStmt(new WriteHeapStmt("b", new ValueExp(new IntValue(2))),
                                                                    new CompStmt(new ConditionalAssignmentStatement("v", new RelationalExp(new ReadHeapExp(new VarExp("a")), new ReadHeapExp(new VarExp("b")), 1), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                    new CompStmt(new ConditionalAssignmentStatement("v", new RelationalExp(new ArithExp('-', new ReadHeapExp(new VarExp("b")), new ValueExp(new IntValue(2))), new ReadHeapExp(new VarExp("a")), 5), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                                            new PrintStmt(new VarExp("v"))
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );

            ex11.typeCheck(new MyDictionary<String, Type>());
            PrgState prg11 = new PrgState(stack11, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex11);
            IRepository repo11 = new Repository(prg11, "log11.txt");
            Controller controller11 = new Controller(repo11);
            repo11.addState(prg11);
            programs.add(controller11);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 11 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }

        // another ex for conditional assignment:
        // bool b;
        //int c;
        //b=true;
        //c=b?100:200;
        //print(c);
        //c= (false)?100:200;
        //print(c);
        /*
        IStmt ex11 = new CompStmt(new VarDeclStmt("b", new BoolType()),
                new CompStmt(new VarDeclStmt("c", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new BoolValue(true))),
                                new CompStmt(new ConditionalAssignmentStmt("c",
                                        new VarExp("b"),
                                        new ValueExp(new IntValue(100)),
                                        new ValueExp(new IntValue(200))),
                                        new CompStmt(new PrintStmt(new VarExp("c")),
                                                new CompStmt(new ConditionalAssignmentStmt("c",
                                                        new ValueExp(new BoolValue(false)),
                                                        new ValueExp(new IntValue(100)),
                                                        new ValueExp(new IntValue(200))),
                                                        new PrintStmt(new VarExp("c"))))))));
         */


        // wait statement
        try {
            IStmt ex12 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new WaitStatement(10),
                                    new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));

            ex12.typeCheck(new MyDictionary<String, Type>());
            PrgState prg12 = new PrgState(stack12, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex12);
            IRepository repo12 = new Repository(prg12, "log12.txt");
            Controller controller12 = new Controller(repo12);
            repo12.addState(prg12);
            programs.add(controller12);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 12 did not pass the typecheck: "+e.getMessage());

            alert.showAndWait();
        }


        // mul exp (relational expression)
        try {
            IStmt ex13 = new CompStmt(new VarDeclStmt("v1", new IntType()),
                    new CompStmt(new VarDeclStmt("v2", new IntType()),
                            new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))),
                                    new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                                            new IfStmt(new RelationalExp(new VarExp("v1"), new ValueExp(new IntValue(0)), 4),
                                                    new PrintStmt(new MULExpression(new VarExp("v1"), new VarExp("v2"))),
                                                    new PrintStmt(new VarExp("v1")))))));

            ex13.typeCheck(new MyDictionary<String, Type>());
            PrgState prg13 = new PrgState(stack13, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex13);
            IRepository repo13 = new Repository(prg13, "log13.txt");
            Controller controller13 = new Controller(repo13);
            repo13.addState(prg13);
            programs.add(controller13);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 13 did not pass the typecheck: "+ e.getMessage());

            alert.showAndWait();
        }


        // repeat...until statement (+ notExp)
        try {
            IStmt ex14 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                            new CompStmt(new RepeatUntilStatement(
                                    new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                            new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                    new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), 3)
                            ),
                                    new CompStmt(new VarDeclStmt("x", new IntType()),
                                            new CompStmt(new VarDeclStmt("y", new IntType()),
                                                    new CompStmt(new VarDeclStmt("z", new IntType()),
                                                            new CompStmt(new VarDeclStmt("w", new IntType()),
                                                                    new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                                                                            new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(2))),
                                                                                    new CompStmt(new AssignStmt("z", new ValueExp(new IntValue(3))),
                                                                                            new CompStmt(new AssignStmt("w", new ValueExp(new IntValue(4))),
                                                                                                    new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))))));

            ex14.typeCheck(new MyDictionary<String, Type>());
            PrgState prg14 = new PrgState(stack14, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex14);
            IRepository repo14 = new Repository(prg14, "log14.txt");
            Controller controller14 = new Controller(repo14);
            repo14.addState(prg14);
            programs.add(controller14);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 14 did not pass the typecheck: "+ e.getMessage());

            alert.showAndWait();
        }


        // for statement
        try {
            IStmt ex15 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(new ForStatement("v", new ValueExp(new IntValue(0)),
                                    new ValueExp(new IntValue(3)),
                                    new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                    new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))))),
                                    new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));

            ex15.typeCheck(new MyDictionary<String, Type>());
            PrgState prg15 = new PrgState(stack15, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex15);
            IRepository repo15 = new Repository(prg15, "log15.txt");
            Controller controller15 = new Controller(repo15);
            repo15.addState(prg15);
            programs.add(controller15);

        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 15 did not pass the typecheck: "+ e.getMessage());

            alert.showAndWait();
        }


        // doWhile statement
        try {
            IStmt ex16 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                            new CompStmt(new DoWhileStatement(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5),
                                    new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                    new PrintStmt(new VarExp("v")))));

            ex16.typeCheck(new MyDictionary<String, Type>());
            PrgState prg16 = new PrgState(stack16, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex16);
            IRepository repo16 = new Repository(prg16, "log16.txt");
            Controller controller16 = new Controller(repo16);
            repo16.addState(prg16);
            programs.add(controller16);
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText("Example 16 did not pass the typecheck: "+ e.getMessage());

            alert.showAndWait();
        }


        programListView.setItems(FXCollections.observableArrayList(programs.stream().
                map(Controller::getName).collect(Collectors.toList())));
    }
    @FXML
    protected void onRunButtonClicked() {
        if (programListView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramRunLayout.fxml"));
                Parent interpreterView = loader.load();
                ProgramRunController controller = loader.getController();
                controller.setController(programs.get(programListView.getSelectionModel().getSelectedIndex()));
                Stage stage = new Stage();
                stage.setTitle("Main Window");
                stage.setScene(new Scene(interpreterView));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}