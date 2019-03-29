package ts.tsc.leftouterjoin.collectionfabric.lists;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<LineInterface> toJoinList = ((ArrayList<LineInterface>)getList(toJoinTableCollection));

        for (LineInterface leftTable : listTable) {

            boolean idFound = false;
            int size = toJoinList.get(0).getValuableCellsCount();

            /*
             * Ищем во второй таблице строки с таким же ключом
             */
            for (LineInterface rightTable : toJoinList) {
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
     * Добавление строки в коллекцию
     * @param stroke интерфейс строки
     */
    @Override
    public void add(LineInterface stroke) {
        listTable.add(stroke);
    }

    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */
    @Override
    public CollectionFabricInterface setCollection(CollectionFabricInterface table) {
        listTable = (ArrayList<LineInterface>) getList(table);
        return this;
    }

    /**
     * Преобразование значений полученной таблицы к Collection<LineInterface>
     * @param table Объект, содержащий таблицу неопределенного типа
     * @return если объект тиипа MapFabric возвращаем его контейнер без преобразования,
     * иначе приводим к типу List
     */
    private Collection<LineInterface> getList(CollectionFabricInterface table) {
        if(table.getClass() == ListFabric.class) {
            return ((ListFabric)table).getListTable();
        } else  {
            return table.getCollection(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public boolean isEmpty() {
        return listTable.isEmpty();
    }

    @Override
    public Stream<LineInterface> getTableStream() {
        return listTable.stream();
    }

    List<LineInterface> getListTable() {
        return this.listTable;
    }
}
