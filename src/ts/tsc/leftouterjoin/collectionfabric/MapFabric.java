package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MapFabric implements CollectionFabricInterface {
    private  Map<Integer, List<LineInterface>> mapTable;

    public MapFabric(Map<Integer, List<LineInterface>> mapTable) {
        this.mapTable = mapTable;
    }


    /**
     * Добавление строки в коллекцию
     * @param stroke интерфейс строки
     */

    @Override
    public void add(LineInterface stroke) {

        List<LineInterface> mapTableValues;
        if(mapTable.containsKey(stroke.getId())) {
            mapTableValues = mapTable.get(stroke.getId());

            if(mapTableValues == null) {
                mapTableValues = new ArrayList<>();
            }
            mapTableValues.add(stroke);

        } else  {
            mapTableValues = new ArrayList<>();
            mapTableValues.add(stroke);
        }

        mapTable.put(stroke.getId(), mapTableValues);

    }

    /**
     * Левостороннее объединение
     * @param toJoinTableCollection правая таблица
     * @param tableLine типа строки таблицы
     * @return объединенная таблицы
     */

    @Override
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine) {

        CollectionFabricInterface requestedTableCollection = new MapFabric(new ConcurrentHashMap<>());
        /*
         * Цикл по левой таблице для поиска ключей в правой
         */
        for (Map.Entry<Integer, List<LineInterface>> leftTableEntry : mapTable.entrySet()) {

            Integer rightMapTableKey = (leftTableEntry.getKey());
            List<LineInterface> leftMapTableValues = leftTableEntry.getValue();
            /*
             * Проходим по списку значений Map с одинаковыми ключами
             */
            for (LineInterface leftMapValue : leftMapTableValues) {

                List<LineInterface> rightMapValues;
                Map<Integer, List<LineInterface>> leftTable = toJoinTableCollection.getMapCollection();
                /*
                 * Если найден совпадающий ключ, то для всех значений с
                 * тем же ключом из правой таблицы совершается объединение
                 */
                if (leftTable.containsKey(rightMapTableKey)) {
                    rightMapValues = leftTable.get(rightMapTableKey);

                    for (LineInterface rightLineValue : rightMapValues) {
                        requestedTableCollection.add(tableLine
                                .setParameters(LineCreator.createLine(leftMapValue, rightLineValue)));
                    }
                } else {
                    /*
                     * Если нет строк с таким же ключом, недостающие ячейки заполняются "null"
                     */
                    int size = leftMapValue.getValueCellsCount();
                    requestedTableCollection.add(tableLine
                            .setParameters(LineCreator.getNotJoinedLine(leftMapValue, size)));
                }

            }

        }

        return requestedTableCollection;
    }


    /**
     * Преобразование таблицы в массив строк
     */

    @Override
    public String[] toStringArray() {
        return mapTable.values()
                .stream()
                .flatMap(Collection::stream)
                .map(LineInterface::toString)
                .toArray(String[]::new);

    }

    @Override
    public Map<Integer, List<LineInterface>> getMapCollection() {
        return mapTable;
    }


    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */

    @Override
    public CollectionFabricInterface addAll(CollectionFabricInterface table) {
        mapTable = table.getMapCollection();
        return this;
    }

    @Override
    public List<LineInterface> getLinkedListCollection() {
        return mapTable.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<LineInterface> getArrayListCollection() {
        return mapTable.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
