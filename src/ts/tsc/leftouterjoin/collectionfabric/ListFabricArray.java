package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.ArrayList;

public class ListFabricArray extends ListFabric{
    public ListFabricArray() {
        super(new ArrayList<>());
    }
    @Override
    public void add(LineInterface stroke) {
        listTable.add(stroke);
    }
}
