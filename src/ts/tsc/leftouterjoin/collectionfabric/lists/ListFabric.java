package ts.tsc.leftouterjoin.collectionfabric.lists;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Фабрика для представления таблиц в виде LinkedList
 */
public class ListFabric implements CollectionFabricInterface {

    List<LineInterface> listTable;

    public ListFabric(List<LineInterface> listTable) {
        this.listTable = listTable;
    }
    /**
     * Левостороннее объединение
     * @param toJoinTableCollection правая таблица
     * @param tableLine типа строки таблицы
     * @return объединенная таблицы
     */
    @Override
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection,
                                                     LineInterface tableLine){

        //Создаем фабрику того же типа, что и текущая
        ListFabric requestedTableCollection = new ListFabric(new ArrayList<>());

        for (LineInterface leftTable : listTable) {

            boolean idFound = false;
            int size = toJoinTableCollection.getArrayListCollection().get(0).getValuableCellsCount();

            /*
             * Ищем во второй таблице строки с таким же ключом
             */
            for (LineInterface rightTable : toJoinTableCollection.getArrayListCollection()) {
                if(leftTable.getId().compareTo(rightTable.getId())==0) {
                    requestedTableCollection.add(tableLine
                            .setParameters(LineCreator.createLine(leftTable, rightTable)));
                    idFound = true;
                }
            }

            /*
             * Если не найдены строки стаким же ключом, то оставшиеся ячейки заполняются "null"
             */
            if(!idFound) {
                requestedTableCollection.add(tableLine
                        .setParameters(LineCreator.createNotJoinedLine(leftTable, size)));
            }

        }

        return requestedTableCollection;
    }


    /**
     * Преобразование таблицы в сортированный по ключу массив строк
     */
    @Override
    public String[] toStringArray() {
        return listTable.stream()
                .sorted(Comparator.comparing(LineInterface::getId))
                .map(LineInterface::toString)
                .toArray(String[]::new);
    }

    /**
     * Добавление строки в коллекцию
     * @param stroke интерфейс строки
     */
    @Override
    public void add(LineInterface stroke) {
        listTable.add(stroke);
    }

    @Override
    public List<LineInterface> getArrayListCollection() {
        return listTable;
    }

    @Override
    public List<LineInterface> getLinkedListCollection() {
        return new LinkedList<>(listTable);
    }

    @Override
    public Map<Integer, List<LineInterface>> getMapCollection() {
        return  listTable.stream()
                .collect(Collectors.groupingBy(LineInterface::getId));
    }

    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */
    @Override
    public CollectionFabricInterface setCollection(CollectionFabricInterface table) {
        listTable = table.getArrayListCollection();
        return this;
    }

    @Override
    public boolean isEmpty() {
        return listTable.isEmpty();
    }
}
