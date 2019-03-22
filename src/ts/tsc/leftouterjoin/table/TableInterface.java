package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;

public interface TableInterface {
    CollectionFabricInterface getCollection(CollectionFabricInterface requestedTableCollection);
    int doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection);
    boolean isEmpty();
    String[] printTable();
    String toString();
}
