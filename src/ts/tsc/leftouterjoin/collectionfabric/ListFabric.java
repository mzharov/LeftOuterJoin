package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.List;

public abstract class ListFabric implements CollectionFabricInterface {
    protected List<LineInterface> listTable;
    public ListFabric(List<LineInterface> listTable) {
        this.listTable = listTable;
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
