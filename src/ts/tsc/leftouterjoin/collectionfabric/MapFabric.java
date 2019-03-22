package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.Map;

public class MapFabric implements CollectionFabricInterface {
    private Map<Integer, LineInterface> mapTable;

    public MapFabric(Map<Integer,LineInterface> mapTable) {
        this.mapTable = mapTable;
    }

    @Override
    public void add(LineInterface stroke) {
        mapTable.putIfAbsent(stroke.getId(), stroke.getLine());
    }

    @Override
    public CollectionFabricInterface getCollection(CollectionFabricInterface collectionFabricTable) {
        return null;
    }

    @Override
    public int doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return mapTable.isEmpty();
    }

    @Override
    public String[] printTable() {
        return mapTable.values()
                .stream()
                .map(LineInterface::toString)
                .toArray(String[]::new);
    }

    @Override
    public String toString() {
        return null;
    }
}
