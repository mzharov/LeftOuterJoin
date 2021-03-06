package ts.tsc.leftouterjoin.joiner;

import ts.tsc.leftouterjoin.table.Table;
import ts.tsc.leftouterjoin.table.line.TableLine;
import ts.tsc.leftouterjoin.tablecontainers.ContainerTableInterface;
import ts.tsc.leftouterjoin.tablecontainers.lists.ArrayListTable;
import ts.tsc.leftouterjoin.tablecontainers.lists.LinkedListTable;
import ts.tsc.leftouterjoin.tablecontainers.maps.MapTable;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

class LeftOuterJoin {

    private static Table firstTable;
    private static Table secondTable;

    private static void errorLog(int tableCondition, String path) {
        if(tableCondition == Table.FILE_NOT_FOUND) {
            System.out.println("Не найден файл: " + path);
        }
        if(tableCondition == Table.IO_EXCEPTION) {
            System.out.println("Ошибка в ходе чтения файла: " + path);
        }
    }
    /**
     * Вывод данных из табличного представления интерфейса
     * @param table интерфейс таблицы
     */
    private static void printTable(String message, final ContainerTableInterface table) {
        String[] tableArray = table.toStringArray();

        System.out.println(message);
        int lineNumber = 0;
        for (String line : tableArray) {
            System.out.println("[" + ++lineNumber + "] " + line);
        }
    }
    /**
     * Левосторонне объединение
     */
    private static void doLeftOuterJoin(String message) {
        ContainerTableInterface leftOuterJoin =
                firstTable.doLeftOuterJoin(secondTable.getTableCollection(), new TableLine());
        printTable(message, leftOuterJoin);
    }
    /**
     * Преобразование табличного представления из одних коллекций в другие
     * @param firstTableCollection интерфейс первой коллекции
     * @param secondTableCollection интерфейс второй коллекции
     */
    private static void transaction(String message, ContainerTableInterface firstTableCollection,
                                    ContainerTableInterface secondTableCollection) {
        firstTable.setNewCollection(firstTableCollection);
        secondTable.setNewCollection(secondTableCollection);
        doLeftOuterJoin(message);
    }

    private static void isEmpty(Table table, String path) {
        if(table.isEmpty())
            System.out.println("Таблица в файле " + path + " пуста. " +
                    "Левостороннее объединение не может быть совершено");
    }

    public static void main(String[] args) {

        String pathToFirstTable = "firstTable";
        String pathToSecondTable = "secondTable";

        firstTable = new Table(pathToFirstTable, new ArrayListTable(new ArrayList<>()));
        secondTable = new Table(pathToSecondTable, new ArrayListTable(new ArrayList<>()));

        int fTableCondition = firstTable.readFile();
        int sTableCondition = secondTable.readFile();

        errorLog(fTableCondition, pathToFirstTable);
        errorLog(sTableCondition, pathToSecondTable);

        isEmpty(firstTable, pathToFirstTable);
        isEmpty(secondTable, pathToSecondTable);

        if(fTableCondition == Table.SUCCESS && sTableCondition == Table.SUCCESS
                && !firstTable.isEmpty() && !secondTable.isEmpty()) {
            System.out.println("---Файлы: " + pathToFirstTable + " и " + pathToSecondTable + " прочитаны---");

            String message = "---Первая таблица:---";
            printTable(message, firstTable.getTableCollection());
            message = "---Вторая таблица:---";
            printTable(message, secondTable.getTableCollection());

            message = "---Левостороннее объединение таблиц, хранящихся в ArrayList---";
            doLeftOuterJoin(message);

            message = "---Передача таблиц для хранения в LinkedList и их левосторонее объединение---";
            transaction(message, new LinkedListTable(), new LinkedListTable());

            message = "---Передача таблиц для хранения в Map и их левосторонее объединение--";
            transaction(message, new MapTable(new ConcurrentHashMap<>()), new MapTable(new ConcurrentHashMap<>()));

        }

    }
}
