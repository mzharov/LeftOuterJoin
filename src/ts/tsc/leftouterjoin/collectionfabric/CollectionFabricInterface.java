package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;
import ts.tsc.leftouterjoin.table.TableInterface;


public interface CollectionFabricInterface extends TableInterface {
    void add(LineInterface stroke);
}
