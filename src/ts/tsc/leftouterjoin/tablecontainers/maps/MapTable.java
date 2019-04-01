package ts.tsc.leftouterjoin.tablecontainers.maps;

import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.TableLineInterface;
import ts.tsc.leftouterjoin.tablecontainers.ContainerTableInterface;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTable implements ContainerTableInterface {
    private Map<Integer, List<TableLineInterface>> mapTable;

    public MapTable(Map<Integer, List<TableLineInterface>> mapTable) {
        this.mapTable = mapTable;
    }


    /**
     * Добавление строки в коллекцию
     * @param stroke интерфейс строки
     */

    @Override
    public void add(TableLineInterface stroke) {

        List<TableLineInterface> mapTableValues;
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
    public ContainerTableInterface doLeftOuterJoin(ContainerTableInterface toJoinTableCollection,
                                                   TableLineInterface tableLine) {

        ContainerTableInterface requestedTableCollection = new MapTable(new ConcurrentHashMap<>());
        Map<Integer, List<TableLineInterface>> rightTable = getMap(toJoinTableCollection);

        /*
         * Цикл по левой таблице для поиска ключей в правой
         */
        for (Map.Entry<Integer, List<TableLineInterface>> leftTableEntry : mapTable.entrySet()) {

            Integer leftMapTableKey = (leftTableEntry.getKey());
            List<TableLineInterface> leftMapTableValues = leftTableEntry.getValue();
            /*
             * Проходим по списку значений Map с одинаковыми ключами
             */
            for (TableLineInterface leftLineValue : leftMapTableValues) {

                List<TableLineInterface> rightMapValues;
                /*
                 * Если найден совпадающий ключ, то для всех значений с
                 * тем же ключом из правой таблицы совершается объединение
                 */
                if (rightTable.containsKey(leftMapTableKey)) {
                    rightMapValues = rightTable.get(leftMapTableKey);

                    for (TableLineInterface rightLineValue : rightMapValues) {
                        requestedTableCollection.add(tableLine
                                .setParameters(LineCreator.createLine(leftLineValue, rightLineValue)));
                    }
                } else {
                    /*
                     * Если нет строк с таким же ключом, недостающие ячейки заполняются "null"
                     */
                    int size = leftLineValue.getValuableCellsCount();
                    requestedTableCollection.add(tableLine
                            .setParameters(LineCreator.createNotJoinedLine(leftLineValue, size)));
                }

            }

        }

        return requestedTableCollection;
    }


    /**
     * Преобразование  Map<Integer, List<TableLineInterface>> в поток Stream<TableLineInterface>
     * @return поток значений TableLineInterface
     */
    @Override
    public Stream<TableLineInterface> getTableStream() {
        return mapTable.values().stream().flatMap(Collection::stream);
    }

    /**
     * Преобразование значений полученной таблицы к Map<Integer, List<TableLineInterface>>
     * @param table Объект, содержащий таблицу неопределенного типа
     * @return если объект тиипа MapTable возвращаем его контейнер без преобразования,
     * иначе приводим к типу Map
     */
    private Map<Integer, List<TableLineInterface>> getMap(ContainerTableInterface table) {
        if(table.getClass() == MapTable.class) {
            return ((MapTable) table).getMapTable();
        } else {
            return table.getMap(Collectors.groupingBy(
                    TableLineInterface::getId,  Collectors.mapping(
                            TableLineInterface::getLine, Collectors.toList())));
        }
    }

    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */
    @Override
    public ContainerTableInterface setCollection(ContainerTableInterface table) {
        mapTable = table.getMap(Collectors.groupingBy(
                TableLineInterface::getId,  Collectors.mapping(
                        TableLineInterface::getLine, Collectors.toList())));
        return this;
    }

    @Override
    public boolean isEmpty() {
        return mapTable.isEmpty();
    }

    private Map<Integer, List<TableLineInterface>> getMapTable() {
        return this.mapTable;
    }
}
