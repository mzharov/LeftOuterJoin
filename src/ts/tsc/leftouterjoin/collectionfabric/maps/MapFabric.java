package ts.tsc.leftouterjoin.collectionfabric.maps;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapFabric implements CollectionFabricInterface {
    private Map<Integer, List<LineInterface>> mapTable;

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
                mapTableValues = new LinkedList<>();
            }
            mapTableValues.add(stroke);

        } else  {
            mapTableValues = new LinkedList<>();
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
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection,
                                                     LineInterface tableLine) {

        CollectionFabricInterface requestedTableCollection = new MapFabric(new ConcurrentHashMap<>());
        Map<Integer, List<LineInterface>> leftTable = getMap(toJoinTableCollection);

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
                    int size = leftMapValue.getValuableCellsCount();
                    requestedTableCollection.add(tableLine
                            .setParameters(LineCreator.createNotJoinedLine(leftMapValue, size)));
                }

            }

        }

        return requestedTableCollection;
    }


    /**
     * Преобразование таблицы в сортированный по ключу массив строк
     */

    @Override
    public String[] toStringArray() {
        return mapTable.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(LineInterface::getId))
                .map(LineInterface::toString)
                .toArray(String[]::new);

    }

    /**
     * Преобразование  Map<Integer, List<LineInterface>> в поток Stream<LineInterface>
     * @return поток значений LineInterface
     */
    @Override
    public Stream<LineInterface> getTableStream() {
        return mapTable.values().stream().flatMap(Collection::stream);
    }

    /**
     * Преобразование значений полученной таблицы к Map<Integer, List<LineInterface>>
     * @param table Объект, содержащий таблицу неопределенного типа
     * @return если объект тиипа MapFabric возвращаем его контейнер без преобразования,
     * иначе приводим к типу Map
     */
    private Map<Integer, List<LineInterface>> getMap(CollectionFabricInterface table) {
        if(table.getClass() == MapFabric.class) {
            return ((MapFabric) table).getMapTable();
        } else {
            return table.getMap(Collectors.groupingBy(
                    LineInterface::getId,  Collectors.mapping(
                            LineInterface::getLine, Collectors.toList())));
        }
    }

    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */
    @Override
    public CollectionFabricInterface setCollection(CollectionFabricInterface table) {
        mapTable = table.getMap(Collectors.groupingBy(
                LineInterface::getId,  Collectors.mapping(
                        LineInterface::getLine, Collectors.toList())));
        return this;
    }

    @Override
    public boolean isEmpty() {
        return mapTable.isEmpty();
    }

    private Map<Integer, List<LineInterface>> getMapTable() {
        return this.mapTable;
    }
}
