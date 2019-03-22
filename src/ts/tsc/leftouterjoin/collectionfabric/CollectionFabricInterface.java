package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;
import ts.tsc.leftouterjoin.table.TableInterface;

import java.util.List;
import java.util.Map;


public interface CollectionFabricInterface extends TableInterface {
    void add(LineInterface stroke);
    List<LineInterface> getListCollection();
    Map<Integer, LineInterface> getMapCollection();
}
