package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.listcollections.CollectionInterface;
import ts.tsc.leftouterjoin.table.LineInterface;

public class ListFabric implements CollectionFabricInterface {
    //private List<LineInterface> listTable;
    private CollectionInterface listTable;

    public ListFabric(CollectionInterface listTable) {
        this.listTable = listTable;
    }

    @Override
    public void add(LineInterface stroke) {
        listTable.add(stroke);
    }

    @Override
    public CollectionFabricInterface getCollection(CollectionFabricInterface collectionFabricTable) {
        return null;
    }

    @Override
    public int doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection){
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return listTable.isEmpty();
    }

    @Override
    public String[] printTable() {
        return listTable.stream()
                .map(LineInterface::toString)
                .toArray(String[]::new);
    }
    @Override
    public String toString() {
        return null;
    }
}
