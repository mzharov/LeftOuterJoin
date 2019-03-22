package tsc.ts.leftouterjoin.table;

import tsc.ts.leftouterjoin.collectionfabric.CollectionFabricInterface;

public class Table implements tableInterface{
    private String filePath;
    public CollectionFabricInterface tableCollection;

    public Table(String filePath,
                 CollectionFabricInterface tableCollection) {
        this.filePath = filePath;
        this.tableCollection = tableCollection;
    }
    public void readFile() {
        tableCollection.add(new Stroke(1, "hello"));
    }

    @Override
    public CollectionFabricInterface getCollection(CollectionFabricInterface requestedTableCollection) {
        tableCollection.getCollection(requestedTableCollection);
        return null;
    }

    @Override
    public void doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection) {
        tableCollection.doLeftOuterJoin(toJoinTableCollection);
    }
    public CollectionFabricInterface getTableCollection() {
        return tableCollection;
    }
}
