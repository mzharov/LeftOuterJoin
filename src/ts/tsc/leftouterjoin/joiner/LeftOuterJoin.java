package ts.tsc.leftouterjoin.joiner;

import ts.tsc.leftouterjoin.collectionfabric.ListFabricLinked;
import ts.tsc.leftouterjoin.collectionfabric.MapFabric;
import ts.tsc.leftouterjoin.table.Table;

import java.util.concurrent.ConcurrentHashMap;

public class LeftOuterJoin {

    private static void printTable(final Table table) {
        String[] tableArray = table.printTable();
        System.out.println();
        for (String line : tableArray) {
            System.out.println(line);
        }
    }
    public static void main(String[] args) {

        String path1 = "C:\\Programming\\LeftOuterJoin\\firstTable";
        String path2 = "C:\\Programming\\LeftOuterJoin\\secondTable";

        Table firstTable = new Table(path1, new ListFabricLinked());
        Table secondTable = new Table(path2, new MapFabric(new ConcurrentHashMap<>()));

        if(firstTable.readFile() == Table.SUCCESS) {
            System.out.println("Файл 1 прочитан");
        }
        if(secondTable.readFile() == Table.SUCCESS) {
            System.out.println("Файл 2 прочитан");
        }
        printTable(firstTable);
        printTable(secondTable);


        firstTable.doLeftOuterJoin(secondTable.getTableCollection());
    }
}