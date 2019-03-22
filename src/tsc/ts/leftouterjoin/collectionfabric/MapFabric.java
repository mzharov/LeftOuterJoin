package tsc.ts.leftouterjoin.collectionfabric;

import tsc.ts.leftouterjoin.table.StrokeInterface;

import java.util.Map;

public class MapFabric implements CollectionFabricInterface {
    private Map<Integer,StrokeInterface> mapTable;

    MapFabric(Map<Integer,StrokeInterface> mapTable) {
        this.mapTable = mapTable;
    }

    @Override
    public void add(StrokeInterface stroke) {
        mapTable.putIfAbsent(stroke.getId(), stroke.getStroke());
    }

    @Override
    public CollectionFabricInterface getCollection(CollectionFabricInterface collectionFabricTable) {
        return null;
    }

    @Override
    public int doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection) {
        return 0;
    }
}
