package org.example.HighlightingTableViewSampleAfterSort;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;

import java.util.List;

public class MyTable<S> extends TableView {

    private StyleChangingRowFactory<Person> rowFactory;

    private List<Integer>  HighlightedRow;

    @Override
    public void sort() {
        super.sort();
        System.out.println("test custom sort");

        rowFactory = new StyleChangingRowFactory<>("highlightedRow");
        this.setRowFactory(rowFactory);

//        getHighlightedRow().set(0,this.getItems().indexOf(this.getItems().get(HighlightedRow.get(0))));
        getHighlightedRow().set(0,2);

        rowFactory.getStyledRowIndices().clear();
        rowFactory.getStyledRowIndices().setAll(this.getHighlightedRow());

        HighlightedRow = rowFactory.getStyledRowIndices();
        System.out.println(HighlightedRow);
        for (Integer rowNumber:
                HighlightedRow) {
            Person person = (Person) this.getItems().get(rowNumber);
            System.out.println(person);
            System.out.println(this.getItems().indexOf(person));

        }
    }

    public List<Integer> getHighlightedRow() {
        return HighlightedRow;
    }

    public void setHighlightedRow(List<Integer> highlightedRow) {
        HighlightedRow = highlightedRow;
    }

//    @Override
//    public void setOnSort(EventHandler value) {
//        super.setOnSort(value);
//        rowFactory = (StyleChangingRowFactory<Person>) this.getRowFactory();
//        HighlightedRow = rowFactory.getStyledRowIndices();
//
//        for (Integer rowNumber:
//                HighlightedRow) {
//            Person person = (Person) this.getItems().get(rowNumber);
//            System.out.println(person);
//            System.out.println(this.getItems().indexOf(person));
//
//        }
//
//    }
}
