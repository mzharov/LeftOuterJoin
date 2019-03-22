package tsc.ts.leftouterjoin.table;

import tsc.ts.leftouterjoin.collectionfabric.CollectionFabricInterface;

public interface tableInterface {
    CollectionFabricInterface getCollection(CollectionFabricInterface requestedTableCollection);
    int doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection);
}
