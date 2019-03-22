package ts.tsc.leftouterjoin.listcollections;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.List;

public class ConcreteLinkedList implements CollectionInterface {
    private List<LineInterface> listTable;

    public ConcreteLinkedList(List<LineInterface> listTable) {
        this.listTable = listTable;
    }

    @Override
    public void add(LineInterface stroke) {

    }
}
