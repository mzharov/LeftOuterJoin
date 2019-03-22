package tsc.ts.leftouterjoin.joiner;

import tsc.ts.leftouterjoin.collectionfabric.ListFabric;
import tsc.ts.leftouterjoin.table.Table;

import java.util.ArrayList;

public class LeftOuterJoin {
    public static void main(String[] args) {
        Table firstTable = new Table("path1", new ListFabric(new ArrayList<>()));
        Table secondTable = new Table("path2", new ListFabric(new ArrayList<>()));

        firstTable.readFile();
        secondTable.readFile();
        firstTable.doLeftOuterJoin(secondTable.getTableCollection());
    }
}
