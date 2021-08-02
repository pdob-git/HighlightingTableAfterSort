package org.example.HighlightingTableViewSampleAfterSort;

import javafx.scene.control.TableView;

import java.util.List;

public class MyTable<S> extends TableView {

    private StyleChangingRowFactory<Person> rowFactory;

    private List<Integer> HighlightedRows;
    private Person personHighlighted;

    private AfterSort afterSort;


    @Override
    public void sort() {
        super.sort();
        afterSort.afterSortAction();
    }

    public List<Integer> getHighlightedRows() {
        return HighlightedRows;
    }

    public void setHighlightedRows(List<Integer> highlightedRows) {
        HighlightedRows = highlightedRows;
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
