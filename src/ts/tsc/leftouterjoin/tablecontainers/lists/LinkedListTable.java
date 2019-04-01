package ts.tsc.leftouterjoin.tablecontainers.lists;

import ts.tsc.leftouterjoin.table.line.LineComparator;
import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.TableLineInterface;
import ts.tsc.leftouterjoin.tablecontainers.ContainerTableInterface;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class LinkedListTable extends ArrayListTable {

    public LinkedListTable() {
        super(new LinkedList<>());
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
        /*
         * Создание объединенной таблицы того же типа, что и левая
         * преобразование полученной таблицы к тому же типу, что и левая
         */
        ArrayListTable requestedTableCollection = new ArrayListTable(new LinkedList<>());
        LinkedList<TableLineInterface> rightTable = (LinkedList<TableLineInterface>) getList(toJoinTableCollection);
        /*
         * Сортировка обоих списков
         */
        rightTable.sort(new LineComparator());
        listTable.sort(new LineComparator());

        Iterator<TableLineInterface> frontIteratorLeft = listTable.iterator();
        ListIterator<TableLineInterface> frontIteratorRight = rightTable.listIterator();

        TableLineInterface headRight = frontIteratorRight.next();
        int headLeftCopyId = -1;
        int size = rightTable.get(0).getValuableCellsCount();
        int index = 0;

        /*
         * Устанавливаем указатели на начало в обоих списках.
         * Если во втором находится строка с таким же ключом, объединяем их,
         * сдвигаем указатель второго списка на одну позицию вперед и
         * снова сравниваем с ключом из первого списка.
         * И так продолжаем пока ключи не станут различны.
         * На следующей итерации сдвигаем указатель первого списка и сравниваем значение
         * ключа со значением ключа первого списка на предыдущей итерации.
         * Если они одинаковы, возвращаем указатель втрого списка
         * на первый элемент из диапазона строк, с такими же значениями ключа,
         * если нет, то продолжаем идти по второму списку с уже существующего указателя.
         *
         * Если ключ из первого списка меньше ключа во втором,
         * переходим к следующему шагу итерации по первому списку.
         * Если ключ из первого больше - сдвигаем указатель во втором,
         * пока они не станут равны или ключ из первого не станет больше ключа из второго
         *
         * Если не будет найдено во втором списке строки с таким же ключом, то отсутствующие ячейки
         * в строке заполняются "null".
         */
        while (frontIteratorLeft.hasNext()) {
            /*
             * итератор с головы списка
             */
            TableLineInterface headLeft = frontIteratorLeft.next();

            boolean exist = false;

            if(headLeft.getId().compareTo(headLeftCopyId) == 0) {
                frontIteratorRight = rightTable.listIterator(--index);
                headRight = frontIteratorRight.next();
            }

            boolean loop = true;

            index = frontIteratorRight.nextIndex();

            while (loop && frontIteratorRight.hasNext()) {
                if(headLeft.getId().compareTo(headRight.getId()) == 0) {
                    requestedTableCollection.add(tableLine
                            .setParameters(LineCreator.createLine(headLeft, headRight)));
                    headRight = frontIteratorRight.next();
                    exist = true;
                } else if(headLeft.getId().compareTo(headRight.getId()) > 0) {
                    headRight = frontIteratorRight.next();
                } else {loop = false;}
            }

            if(!exist) {
                requestedTableCollection.add(tableLine
                        .setParameters(LineCreator.createNotJoinedLine(headLeft, size)));
            }

            headLeftCopyId = headLeft.getId();
        }

        return requestedTableCollection;
    }

    /**
     * Преобразование фабрики одной коллекции в
     * фабрику другой
     * @param table исходная фабрика
     * @return преобразованная фабрика
     */
    @Override
    public ContainerTableInterface setCollection(ContainerTableInterface table) {
        listTable = (LinkedList<TableLineInterface>) getList(table);
        return this;
    }

    /**
     * Преобразование значений полученной таблицы к LinkedList<TableLineInterface>
     * @param table Объект, содержащий таблицу неопределенного типа
     * @return если объект тиипа MapFabric возвращаем его контейнер без преобразования,
     * иначе приводип к типу LinkedList
     */
    private Collection<TableLineInterface> getList(ContainerTableInterface table) {
        if(table.getClass() == LinkedListTable.class) {
            return ((LinkedListTable)table).getListTable();
        } else  {
            return table.getCollection(Collectors.toCollection(LinkedList::new));
        }
    }
}
