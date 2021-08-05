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
    private SortedList<Person> sortedData;
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

        // 1. Wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(data);

        // 2. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 3. Add sorted (and filtered) data to the table.
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

            System.out.println("Highlighted Rows: " + table.getHighlightedRows());

            if (table.getHighlightedRows().size() == 0) {
                table.setPersonHighlighted(null);
                return;
            }

            table.setPersonHighlighted((Person) table.getItems().get(table.getHighlightedRows().get(0)));
            if (table.getPersonHighlighted() == null){
                return;
            }
            showHighlightedRows(table.getHighlightedRows());

            System.out.println("-----Set On Sort End");
        });

        table.setAfterSort(() -> {
            System.out.println("After Sort is working");
            System.out.println("-----AfterSort  start");

            if (table.getPersonHighlighted() == null){
                return;
            }

            System.out.println("Highlighted person: " + table.getPersonHighlighted());
            rowFactory.getStyledRowIndices().clear();

//            table.getSelectionModel().select(table.getPersonHighlighted());
//            System.out.println("Selected row for person: " + table.getSelectionModel().getSelectedIndices());
//
//            table.getHighlightedRows().add(table.getSelectionModel().getFocusedIndex());
//            System.out.println("Focused index: " + table.getSelectionModel().getFocusedIndex());
//            table.getSelectionModel().clearSelection();

            table.getHighlightedRows().add(table.getItems().indexOf(table.getPersonHighlighted()));

            final StyleChangingRowFactory<Person> rowFactoryLocal = new StyleChangingRowFactory<>("highlightedRow");
            table.setRowFactory(rowFactoryLocal);

            rowFactoryLocal.getStyledRowIndices().setAll(table.getHighlightedRows());

            table.setHighlightedRows(rowFactoryLocal.getStyledRowIndices());

            System.out.println("Highlighted Rows: " + table.getHighlightedRows());
            showHighlightedRows(table.getHighlightedRows());

            System.out.println("-----AfterSort sort end");
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


}