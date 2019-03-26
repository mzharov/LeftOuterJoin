package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;
import ts.tsc.leftouterjoin.table.TableInterface;

import java.util.List;
import java.util.Map;


/**
 * Интерфейс фабрик с основными методами,
 * которые должны быть реализованы в фабриках
 */
public interface CollectionFabricInterface extends TableInterface {
    void add(LineInterface stroke);
    List<LineInterface> getArrayListCollection();
    List<LineInterface> getLinkedListCollection();
    Map<Integer, List<LineInterface>> getMapCollection();
    CollectionFabricInterface setCollection(CollectionFabricInterface table);
    String[] toStringArray();
}
