package ts.tsc.leftouterjoin.collectionfabric.lists;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.table.line.LineComparator;
import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ListFabricLinked extends ListFabric {

    public ListFabricLinked() {
        super(new LinkedList<>());
    }

    private int doCompare(LineInterface head,
                          LineInterface leftTable,
                          ListFabric requestedTableCollection,
                          LineInterface tableLine) {

        if(leftTable.getId().compareTo(head.getId())== 0) {
            requestedTableCollection.add(tableLine
                    .setParameters(LineCreator.createLine(leftTable, head)));
            return 1;
        }
        return 0;
    }
    private void rightTableSearch(final LineInterface leftTable,
                                  final LinkedList<LineInterface> rightTable,
                                  ListFabric requestedTableCollection,
                                  LineInterface tableLine) {
        /*
         * Итераторы для прохода с начала и с конца по правой таблице
         */
        Iterator<LineInterface> frontIterator =
                rightTable.iterator();
        Iterator<LineInterface> backwardIterator =
                rightTable.descendingIterator();

        int size = rightTable.get(0).getValuableCellsCount();

        if(rightTable.peek() == null) return;
        else {
            /*
             * Если ключ из левой таблицы не входит
             * в диапазон значений в правой таблице,
             * то переходим к следующей итерации
             */
            int id = rightTable.peek().getId();
            if(leftTable.getId().compareTo(id) < 0
                    || leftTable.getId().compareTo(rightTable.getLast().getId()) > 0) {
                requestedTableCollection.add(tableLine
                        .setParameters(LineCreator.createNotJoinedLine(leftTable, size)));
                return;
            }
        }
        /*
         * Проходим по второй таблице с обеих сторон в поисках такого же ключа
         */
        int idFound = 0;

        //Переменная для хранения указателя с хвоста
        LineInterface wall = null;

        while (frontIterator.hasNext() && backwardIterator.hasNext()) {
            /*
             * Если найдено совпадение для правого итератора
             */
            LineInterface head = frontIterator.next();
            if (head.equals(wall)) break; // если итераторы сошлись, выходим из цикла
            idFound +=doCompare(head, leftTable, requestedTableCollection, tableLine);

            LineInterface end = backwardIterator.next();
            if (head.equals(end)) break; // если итераторы сошлись, выходим из цикла
            /*
             * Если найдено совпадение для левого итератора
             */
            idFound +=doCompare(end, leftTable, requestedTableCollection, tableLine);
            wall = end;
        }
        /*
         * Если совпадений не найдено недостающие строки заполняются "null"
         */
        if(idFound <= 0) {
            requestedTableCollection.add(tableLine
                    .setParameters(LineCreator.createNotJoinedLine(leftTable, size)));
        }

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
        /*
         * Создание объединенной таблицы того же типа, что и левая
         * преобразование полученной таблицы к тому же типу, что и левая
         */
        ListFabric requestedTableCollection = new ListFabric(new LinkedList<>());
        LinkedList<LineInterface> rightTable = (LinkedList<LineInterface>) getList(toJoinTableCollection);
        /*
         * Сортировка обоих списков
         */
        rightTable.sort(new LineComparator());
        listTable.sort(new LineComparator());

        Iterator<LineInterface> frontIteratorLeft =
                listTable.iterator();
        Iterator<LineInterface> backwardIteratorLeft =
                ((LinkedList<LineInterface>)listTable).descendingIterator();

        Iterator<LineInterface> frontIteratorRight = rightTable.iterator();
        Iterator<LineInterface> backwardIteratorRight = rightTable.descendingIterator();


        LineInterface wall = null;
        LineInterface headRight = frontIteratorRight.next();
        LineInterface tailRight = backwardIteratorRight.next();
        int size = rightTable.get(0).getValuableCellsCount();

        while (frontIteratorLeft.hasNext() && backwardIteratorLeft.hasNext()) {
            /*
             * итератор с головы списка
             */
            LineInterface headLeft = frontIteratorLeft.next();

            if (headLeft.equals(wall)) break;
            //rightTableSearch(head, rightTable, requestedTableCollection, tableLine);

            int rightIterator = 0;
            Iterator<LineInterface> headRightDuplicate = rightTable.iterator();
            int position = frontIteratorRight.nextIn


            do {
                if(headLeft.getId().compareTo(headRight.getId()) == 0) {
                    requestedTableCollection.add(tableLine
                            .setParameters(LineCreator.createLine(headLeft, tableLine)));
                    headRight = frontIteratorRight.next();
                    ++rightIterator;
                } else {
                    break;
                }
            } while(true);

            Iterator<LineInterface> headRightDuplicate = rightTable.iterator();
            while (rightIterator > 0) {

                headRightDuplicate.next();

                --rightIterator;
            }


            /*
             * итератор с хвоста списка
             */
            LineInterface end = backwardIterator.next();
            if (head.equals(end)) break; // если итераторы указывают на один элемент, выходим из цикла
            rightTableSearch(end, rightTable, requestedTableCollection, tableLine);
            wall = end;
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
    public CollectionFabricInterface setCollection(CollectionFabricInterface table) {
        listTable = (LinkedList<LineInterface>) getList(table);
        return this;
    }

    /**
     * Преобразование значений полученной таблицы к LinkedList<LineInterface>
     * @param table Объект, содержащий таблицу неопределенного типа
     * @return если объект тиипа MapFabric возвращаем его контейнер без преобразования,
     * иначе приводип к типу LinkedList
     */
    private Collection<LineInterface> getList(CollectionFabricInterface table) {
        if(table.getClass() == ListFabricLinked.class) {
            return ((ListFabricLinked)table).getListTable();
        } else  {
            return table.getCollection(Collectors.toCollection(LinkedList::new));
        }
    }
}
