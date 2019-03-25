package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;

public interface TableInterface {
    CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine);
}
