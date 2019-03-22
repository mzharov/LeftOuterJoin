package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.List;
import java.util.Map;

public class MapFabric implements CollectionFabricInterface {
    private final Map<Integer, LineInterface> mapTable;

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
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine) {
        return null;
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

    @Override
    public Map<Integer, LineInterface> getMapCollection() {
        return mapTable;
    }

    @Override
    public List<LineInterface> getListCollection() {
        /*
         * TODO
         */
        return null;
    }
}
