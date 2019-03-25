package ts.tsc.leftouterjoin.joiner;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;
import ts.tsc.leftouterjoin.collectionfabric.ListFabric;
import ts.tsc.leftouterjoin.collectionfabric.ListFabricLinked;
import ts.tsc.leftouterjoin.collectionfabric.MapFabric;
import ts.tsc.leftouterjoin.table.Table;
import ts.tsc.leftouterjoin.table.TableLine;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

class LeftOuterJoin {

    private static Table firstTable;
    private static Table secondTable;


    /**
     * Вывод данных из табличного представления интерфейса
     * @param table интерфейс таблицы
     */
    private static void printTable(final CollectionFabricInterface table) {
        String[] tableArray = table.toStringArray();

        for (String line : tableArray) {
            System.out.println(line);
        }
    }

    /**
     * Левосторонне объединение
     */
    private static void doLeftOuterJoin() {
        CollectionFabricInterface leftOuterJoin =
                firstTable.doLeftOuterJoin(secondTable.getTableCollection(), new TableLine());
        printTable(leftOuterJoin);
    }

    /**
     * Преобразование табличного представления из одних коллекций в другие
     * @param firstTableCollection интерфейс первой коллекции
     * @param secondTableCollection интерфейс второй коллекции
     */
    private static void transaction(CollectionFabricInterface firstTableCollection,
                                    CollectionFabricInterface secondTableCollection) {
        firstTable.setNewCollection(firstTableCollection);
        secondTable.setNewCollection(secondTableCollection);
        doLeftOuterJoin();
    }

    public static void main(String[] args) {

        String path1 = "firstTable";
        String path2 = "secondTable";

        firstTable = new Table(path1, new ListFabric(new ArrayList<>()));
        secondTable = new Table(path2, new ListFabric(new ArrayList<>()));

        if(firstTable.readFile() == Table.SUCCESS && secondTable.readFile() == Table.SUCCESS) {
            System.out.println("Файлы: " + path1 + " и " + path2 + " прочитаны");

            System.out.println("Первая таблица:");
            printTable(firstTable.getTableCollection());
            System.out.println("Вторая таблица:");
            printTable(secondTable.getTableCollection());

            System.out.println("---Левостороннее объединение ArrayList---");
            doLeftOuterJoin();

            System.out.println("---Передача параметров в отсортированный LinkedList и левосторонее объединение---");
            transaction(new ListFabricLinked(), new ListFabricLinked());

            System.out.println("---Передача параметров в Map и и левосторонее объединение--");
            transaction(new MapFabric(new ConcurrentHashMap<>()), new MapFabric(new ConcurrentHashMap<>()));

        } else {
            System.out.println("Ошибка в ходе чтения файлов: " + path1 + " и/или " + path2);
        }

    }
}
