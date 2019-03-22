package tsc.ts.leftouterjoin.collectionfabric;

import tsc.ts.leftouterjoin.table.StrokeInterface;
import tsc.ts.leftouterjoin.table.tableInterface;

public interface CollectionFabricInterface extends tableInterface {
    void add(StrokeInterface stroke);
}
