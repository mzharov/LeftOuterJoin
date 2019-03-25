package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class ListFabricLinked extends ListFabric {

    public ListFabricLinked() {
        super(new LinkedList<>());
    }

    class ListComparator implements Comparator<LineInterface> {
        @Override
        public int compare(LineInterface firstElement, LineInterface secondElement) {
            return firstElement.getId().compareTo(secondElement.getId());
        }
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

        int size = rightTable.get(0).getValueCellsCount();

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
                        .setParameters(LineCreator.getNotJoinedLine(leftTable, size)));
                return;
            }
        }
        /*
         * Проходим по второй таблице с обеих сторон в поисках такого же ключа
         */
        boolean idFound = false;

        while (frontIterator.hasNext() && backwardIterator.hasNext()) {

            /*
             * Если найдено совпадение для правого итератора
             */
            LineInterface head = frontIterator.next();
            if(leftTable.getId().compareTo(head.getId())== 0) {
                requestedTableCollection.add(tableLine
                        .setParameters(LineCreator.createLine(leftTable, head)));
                idFound = true;
            }

            LineInterface end = backwardIterator.next();
            if (head.equals(end)) break; // если итераторы сошлись, выходим из цикла

            /*
             * Если найдено совпадение для левого итератора
             */
            if(leftTable.getId().compareTo(end.getId())== 0) {
                requestedTableCollection.add(tableLine
                        .setParameters(LineCreator.createLine(leftTable, end)));
                idFound = true;
            }
        }
        /*
         * Если совпадений не найдено недостающие строки заполняются "null"
         */
        if(!idFound) {
            requestedTableCollection.add(tableLine
                    .setParameters(LineCreator.getNotJoinedLine(leftTable, size)));
        }

    }

    /**
     * Левостороннее объединение
     * @param toJoinTableCollection правая таблица
     * @param tableLine типа строки таблицы
     * @return объединенная таблицы
     */

    @Override
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine){
        /*
         * Создание объединенной таблицы того же типа, что и левая
         * преобразование полученной таблицы к тому же типу, что и левая
         */
        ListFabric requestedTableCollection = new ListFabric(new LinkedList<>());
        LinkedList<LineInterface> rightTable = (LinkedList<LineInterface>) toJoinTableCollection.getLinkedListCollection();
        /*
         * Сортировка обоих списков
         */
        rightTable.sort(new ListComparator());
        listTable.sort(new ListComparator());

        Iterator<LineInterface> frontIterator =
                listTable.iterator();
        Iterator<LineInterface> backwardIterator =
                ((LinkedList<LineInterface>)listTable).descendingIterator();

        while (frontIterator.hasNext() && backwardIterator.hasNext()) {
            /*
             * итератор с головы списка
             */
            LineInterface head = frontIterator.next();
            rightTableSearch(head, rightTable, requestedTableCollection, tableLine);
            /*
             * итератор с хвоста списка
             */
            LineInterface end = backwardIterator.next();
            if (head.equals(end)) break; // если итераторы указывают на один элемент, выходим из цикла
            rightTableSearch(end, rightTable, requestedTableCollection, tableLine);
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
    public CollectionFabricInterface addAll(CollectionFabricInterface table) {
        listTable = table.getLinkedListCollection();
        return this;
    }
}
