package com.example.procedures;

import Controller.Controller;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.MyIProcTable;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramRunController implements Initializable {
    public ProgramRunController(){}

    private Controller ctrl;
    private PrgState selectedProgram;

    @FXML
    private TableView<HashMap.Entry<Integer, String>> heapTableView=new TableView<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, Integer> heapAddressColumn=new TableColumn<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, String> heapValueColumn=new TableColumn<>();

    @FXML
    private ListView<String> outputListView=new ListView<>();

    @FXML
    private ListView<String> fileListView= new ListView<>();

    @FXML
    private ListView<Integer> programStatesView=new ListView<>();

    @FXML
    private TableView<Map.Entry<String, String>> symTableView=new TableView<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symVarNameColumn=new TableColumn<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symValueColumn=new TableColumn<>();

    @FXML
    private ListView<String> exeStackView=new ListView<>();

    @FXML
    private TextField nrProgramStatesField=new TextField("");

    @FXML
    private TableView<Map.Entry<String, Pair<List<String>, IStmt>>> procTableView;

    @FXML
    private TableColumn<Map.Entry<String, Pair<List<String>, IStmt>>, javafx.util.Pair<String, List<String>>> procNameTableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Pair<List<String>, IStmt>>, String> procBodyTableColumn;


    public void setController(Controller ctr) {
        ctrl=ctr;

        selectedProgram=ctrl.getRepository().getPrgList().get(0);

        loadData();
    }

    @FXML
    public void setSelectedProgram(){
        if(programStatesView.getSelectionModel().getSelectedIndex()>=0 && programStatesView.getSelectionModel().getSelectedIndex()<=this.ctrl.getRepository().getPrgList().size()){
            selectedProgram=ctrl.getRepository().getPrgList().get(programStatesView.getSelectionModel().getSelectedIndex());
            loadData();
        }
    }

    private void loadData(){
        this.programStatesView.getItems().setAll( ctrl.getRepository().getPrgList().stream().map(PrgState::getId).collect(Collectors.toList()) );

        if(selectedProgram!=null){

            outputListView.getItems().setAll( selectedProgram.getOutConsole().get_list().stream().map(Object::toString).collect(Collectors.toList()));

            fileListView.getItems().setAll(String.valueOf(selectedProgram.getFileTable().getContent().keySet()));

            List<String> executionStackList=selectedProgram.getStack().getStack().stream().map(IStmt::toString).collect(Collectors.toList());
            Collections.reverse(executionStackList);
            exeStackView.getItems().setAll(executionStackList);

            IHeap<Value> heapTable=selectedProgram.getHeap();
            List<Map.Entry<Integer, String>> heapTableList=new ArrayList<>();
            for(Map.Entry<Integer, Value> element:heapTable.getContent().entrySet()){
                Map.Entry<Integer, String> el=new AbstractMap.SimpleEntry<Integer, String>(element.getKey(),element.getValue().toString());
                heapTableList.add(el);
            }
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();

            heapAddressColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            heapValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            IDictionary<String, Value> symbolTable=this.selectedProgram.getTopSymTable();
            List<Map.Entry<String, String>> symbolTableList=new ArrayList<>();
            for(Map.Entry<String, Value> element:symbolTable.getContent().entrySet()){
                Map.Entry<String, String> el=new AbstractMap.SimpleEntry<String, String>(element.getKey(),element.getValue().toString());
                symbolTableList.add(el);
            }
            symTableView.setItems(FXCollections.observableList(symbolTableList));
            symTableView.refresh();

            symVarNameColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()));
            symValueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue()));

            MyIProcTable procTable = selectedProgram.getProcTable();
            List<Map.Entry<String, javafx.util.Pair<List<String>, IStmt>>> procTableList = new ArrayList<>();
            for (Map.Entry<String, javafx.util.Pair<List<String>, IStmt>> entry: procTable.getContent().entrySet()) {
                Map.Entry<String, javafx.util.Pair<List<String>, IStmt>> entry1 = new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue());
                procTableList.add(entry1);
            }
            procTableView.setItems(FXCollections.observableList(procTableList));
            procTableView.refresh();

            procNameTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(new javafx.util.Pair<String, List<String>>(p.getValue().getKey(), p.getValue().getValue().getKey())));
            procBodyTableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue().toString()));

            nrProgramStatesField.setText(Integer.toString(ctrl.getRepository().getSize()));

        }
    }


    @FXML
    public void onRunOneStepButtonPressed() {
        if(ctrl == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Program was not selected!");
            alert.setContentText("Please select a program to execute");
            alert.showAndWait();
            return;
        }

        if(selectedProgram.getStack().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Program is done!");
            alert.setContentText("Please select a new program to execute");
            alert.showAndWait();
            return;
        }

        ctrl.executeOneStep();

        loadData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
