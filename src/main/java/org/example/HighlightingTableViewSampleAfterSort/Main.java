package org.example.HighlightingTableViewSampleAfterSort;

import java.util.*;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class Main extends Application {

    private final MyTable<Person> table = new MyTable<>();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jacob", "Smith", "jacob.smith@example.com"),
                    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                    new Person("Ethan", "Williams", "ethan.williams@example.com"),
                    new Person("Emma", "Jones", "emma.jones@example.com"),
                    new Person("Michael", "Brown", "michael.brown@example.com")
            );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        table.setItems(data);

        table.getColumns().addAll(Arrays.asList(firstNameCol, lastNameCol, emailCol));

        final StyleChangingRowFactory<Person> rowFactory = new StyleChangingRowFactory<>("highlightedRow");
        table.setRowFactory(rowFactory);

        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        final Button highlightButton = new Button("Highlight");
        highlightButton.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedIndices()));
        highlightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rowFactory.getStyledRowIndices().setAll(table.getSelectionModel().getSelectedIndices());
            }
        });

        final Button clearHighlightButton = new Button("Clear Highlights");
        clearHighlightButton.disableProperty().bind(Bindings.isEmpty(rowFactory.getStyledRowIndices()));
        clearHighlightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rowFactory.getStyledRowIndices().clear();
            }
        });

        final Button getHighlightItems = new Button("Show Highlight row number");

        getHighlightItems.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Integer> HighlightedRows = rowFactory.getStyledRowIndices();
                System.out.println("Highlighted Rows: " + HighlightedRows);
                showHighlightedRows(HighlightedRows);
            }
        });

        table.setOnSort(event -> {
            System.out.println("-----Set On Sort Start");

            table.setHighlightedRows(rowFactory.getStyledRowIndices());

            if (table.getHighlightedRows().size() == 0) {
                table.setPersonHighlighted(null);
                System.out.println("-----Set On Sort interrupted");
                return;
            }

            table.setPersonHighlighted((Person) table.getItems().get(table.getHighlightedRows().get(0)));

            if (table.getPersonHighlighted() == null){
                System.out.println("-----Set On Sort interrupted");
                return;
            }
            showHighlightedRows(table.getHighlightedRows());
            System.out.println("Table before sort");
            System.out.println(printTableItems((ObservableList<Person>)table.getItems()));

            System.out.println("-----Set On Sort End");
        });

        table.setAfterSort(() -> {
            System.out.println("After Sort is working");
            System.out.println("-----After Sort  start");

            if (table.getPersonHighlighted() == null){
                System.out.println("-----After Sort interrupted");
                return;
            }

            rowFactory.getStyledRowIndices().clear();

            table.getHighlightedRows().add(table.getItems().indexOf(table.getPersonHighlighted()));

            final StyleChangingRowFactory<Person> rowFactoryLocal = new StyleChangingRowFactory<>("highlightedRow");
            table.setRowFactory(rowFactoryLocal);

            rowFactoryLocal.getStyledRowIndices().setAll(table.getHighlightedRows());

            table.setHighlightedRows(rowFactoryLocal.getStyledRowIndices());


            showHighlightedRows(table.getHighlightedRows());

            System.out.println("Table after sort");
            System.out.println(printTableItems((ObservableList<Person>)table.getItems()));

            System.out.println("-----After Sort end");
        });

        final HBox buttons = new HBox(5);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(highlightButton, clearHighlightButton, getHighlightItems);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, buttons);

        Scene scene = new Scene(vbox, 450, 500);
        stage.setTitle("Highlighting Table View Sample");
        scene.getStylesheets().add(getClass().getResource("css/highlightingTable.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void showHighlightedRows(List<Integer> highlightedRows) {
        if (highlightedRows.size() == 0) {
            return;
        }
        for (Integer rowNumber :
                highlightedRows) {
            Person person = (Person) table.getItems().get(rowNumber);
            System.out.println("Highlighted person: " + person);
            System.out.println("Highlighted person index: " + table.getItems().indexOf(person));

        }
    }

    public String printTableItems(ObservableList<Person> tableItems) {
        StringBuilder stringResult = new StringBuilder();
        for (Person person:
                tableItems) {
            stringResult.append("Index :").append(tableItems.indexOf(person)).append(" ")
                    .append(person.toString()).append("\n");
        }
        stringResult.append("----------------------");
        return stringResult.toString();
    }


}