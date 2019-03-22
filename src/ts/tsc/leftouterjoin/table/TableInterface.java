package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;

public interface TableInterface {
    CollectionFabricInterface getCollection(CollectionFabricInterface requestedTableCollection);
    CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine);
    boolean isEmpty();
    String[] printTable();
    String toString();
}
