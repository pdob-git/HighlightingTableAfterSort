package org.example.HighlightingTableViewSampleAfterSort;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;

import java.util.List;

public class MyTable<S> extends TableView {

    private StyleChangingRowFactory<Person> rowFactory;

    private List<Integer>  HighlightedRow;
    private Person personHighlighted;



    private AfterSort afterSort;

//    @Override
    public void oldsort() {
//        super.sort();
        System.out.println("-----Test custom sort start");

        rowFactory = new StyleChangingRowFactory<>("highlightedRow");
        this.setRowFactory(rowFactory);

//        getHighlightedRow().set(0,this.getItems().indexOf(this.getItems().get(HighlightedRow.get(0))));
//        getHighlightedRow().set(0,2);


        System.out.println("Highlighted person " + getPersonHighlighted());
        rowFactory.getStyledRowIndices().clear();

        this.getSelectionModel().select(getPersonHighlighted());
        getHighlightedRow().set(0,this.getSelectionModel().getFocusedIndex());
        this.getSelectionModel().clearSelection();
        rowFactory.getStyledRowIndices().setAll(this.getHighlightedRow());

        HighlightedRow = rowFactory.getStyledRowIndices();
        System.out.println(HighlightedRow);
        for (Integer rowNumber:
                HighlightedRow) {
            Person person = (Person) this.getItems().get(rowNumber);
            System.out.println(person);
            System.out.println(this.getItems().indexOf(person));

        }
        System.out.println("-----Test custom sort end");
    }

    @Override
    public void sort() {
        super.sort();
        afterSort.afterSortAction();
    }

    public List<Integer> getHighlightedRow() {
        return HighlightedRow;
    }

    public void setHighlightedRow(List<Integer> highlightedRow) {
        HighlightedRow = highlightedRow;
    }

    public Person getPersonHighlighted() {
        return personHighlighted;
    }

    public void setPersonHighlighted(Person personHighlighted) {
        this.personHighlighted = personHighlighted;
    }

    public void setAfterSort(AfterSort afterSort) {
        this.afterSort = afterSort;
    }


}
