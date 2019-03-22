package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.collectionfabric.CollectionFabricInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Table implements TableInterface{
    private String filePath;
    public CollectionFabricInterface tableCollection;
    public static final int FILE_NOT_FOUND = -1;
    public static final int IO_EXCEPTION = 0;
    public static final int SUCCESS = 1;

    public Table(String filePath,
                 CollectionFabricInterface tableCollection) {
        this.filePath = filePath;
        this.tableCollection = tableCollection;
    }

    public int readFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String fileLine;

            while ((fileLine = bufferedReader.readLine()) != null) {

                String[] fileLineArray = LineFormatter.parseLine(fileLine);
                tableCollection.add(new TableLine(Integer.parseInt(fileLineArray[0]), fileLineArray[1]));

            }

        } catch (FileNotFoundException e) {
            return FILE_NOT_FOUND;
        } catch (IOException e) {
            return IO_EXCEPTION;
        }

        return SUCCESS;
    }

    @Override
    public CollectionFabricInterface getCollection(CollectionFabricInterface requestedTableCollection) {
        tableCollection.getCollection(requestedTableCollection);
        return null;
    }

    @Override
    public int doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection) {
        tableCollection.doLeftOuterJoin(toJoinTableCollection);
        return 0;
    }
    public CollectionFabricInterface getTableCollection() {
        return tableCollection;
    }

    @Override public boolean isEmpty() {
        return tableCollection.isEmpty();
    }

    @Override
    public String[] printTable() {
        return tableCollection.printTable();
    }
}