package ts.tsc.leftouterjoin.tablecontainers.lists;

import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.TableLineInterface;
import ts.tsc.leftouterjoin.tablecontainers.ContainerTableInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Фабрика для представления таблиц в виде LinkedList
 */
public class ArrayListTable implements ContainerTableInterface {

    List<TableLineInterface> listTable;

    public ArrayListTable(List<TableLineInterface> listTable) {
        this.listTable = listTable;
    }

    /**
     * Левостороннее объединение
     * @param toJoinTableCollection правая таблица
     * @param tableLine типа строки таблицы
     * @return объединенная таблицы
     */
    @Override
    public ContainerTableInterface doLeftOuterJoin(ContainerTableInterface toJoinTableCollection,
                                                   TableLineInterface tableLine){

        //Создаем фабрику того же типа, что и текущая
        ArrayListTable requestedTableCollection = new ArrayListTable(new ArrayList<>());
        List<TableLineInterface> toJoinList = ((ArrayList<TableLineInterface>)getList(toJoinTableCollection));

        for (TableLineInterface leftTable : listTable) {

            boolean idFound = false;
            int size = toJoinList.get(0).getValuableCellsCount();

            /*
             * Ищем во второй таблице строки с таким же ключом
             */
            for (TableLineInterface rightTable : toJoinList) {
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
    public void add(TableLineInterface stroke) {
        listTable.add(stroke);
    }

    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */
    @Override
    public ContainerTableInterface setCollection(ContainerTableInterface table) {
        listTable = (ArrayList<TableLineInterface>) getList(table);
        return this;
    }

    /**
     * Преобразование значений полученной таблицы к Collection<TableLineInterface>
     * @param table Объект, содержащий таблицу неопределенного типа
     * @return если объект тиипа MapFabric возвращаем его контейнер без преобразования,
     * иначе приводим к типу List
     */
    private Collection<TableLineInterface> getList(ContainerTableInterface table) {
        if(table.getClass() == ArrayListTable.class) {
            return ((ArrayListTable)table).getListTable();
        } else  {
            return table.getCollection(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public boolean isEmpty() {
        return listTable.isEmpty();
    }

    @Override
    public Stream<TableLineInterface> getTableStream() {
        return listTable.stream();
    }

    List<TableLineInterface> getListTable() {
        return this.listTable;
    }
}
