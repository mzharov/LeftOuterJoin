package ts.tsc.leftouterjoin.collectionfabric.lists;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.table.line.LineCreator;
import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.Iterator;
import java.util.LinkedList;

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
        LinkedList<LineInterface> rightTable = (LinkedList<LineInterface>) toJoinTableCollection
                .getLinkedListCollection();
        /*
         * Сортировка обоих списков
         */
        rightTable.sort(new ListComparator());
        listTable.sort(new ListComparator());

        Iterator<LineInterface> frontIterator =
                listTable.iterator();
        Iterator<LineInterface> backwardIterator =
                ((LinkedList<LineInterface>)listTable).descendingIterator();

        LineInterface wall = null;
        while (frontIterator.hasNext() && backwardIterator.hasNext()) {
            /*
             * итератор с головы списка
             */
            LineInterface head = frontIterator.next();
            if (head.equals(wall)) break;
            rightTableSearch(head, rightTable, requestedTableCollection, tableLine);
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
        listTable = table.getLinkedListCollection();
        return this;
    }
}
