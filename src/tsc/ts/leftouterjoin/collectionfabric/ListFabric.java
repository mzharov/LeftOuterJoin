package tsc.ts.leftouterjoin.collectionfabric;

import tsc.ts.leftouterjoin.table.StrokeInterface;

import java.util.List;

public class ListFabric implements CollectionFabricInterface {
    private List<StrokeInterface> listTable;
    public ListFabric(List<StrokeInterface> listTable) {
        this.listTable = listTable;
    }

    @Override
    public void add(StrokeInterface stroke) {
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
}
