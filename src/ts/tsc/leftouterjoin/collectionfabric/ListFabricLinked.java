package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.LinkedList;

public class ListFabricLinked extends ListFabric {
    public ListFabricLinked() {
        super(new LinkedList<>());
    }
    @Override
    public void add(LineInterface stroke) {
        listTable.add(stroke);
    }
}
