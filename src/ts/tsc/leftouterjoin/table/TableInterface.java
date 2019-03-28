package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.table.line.LineInterface;

public interface TableInterface {
    CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection,
                                              LineInterface tableLine);
    boolean isEmpty();
}
