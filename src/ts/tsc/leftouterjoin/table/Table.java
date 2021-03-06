package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.table.line.LineFormatter;
import ts.tsc.leftouterjoin.table.line.TableLine;
import ts.tsc.leftouterjoin.table.line.TableLineInterface;
import ts.tsc.leftouterjoin.tablecontainers.ContainerTableInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class Table implements TableInterface{
    private final String filePath;
    private ContainerTableInterface tableCollection;

    /**
     * Коды состояния чтения файла
     */
    public static final int FILE_NOT_FOUND = -1;
    public static final int IO_EXCEPTION = 0;
    public static final int SUCCESS = 1;

    public Table(String filePath,
                 ContainerTableInterface tableCollection) {
        this.filePath = filePath;
        this.tableCollection = tableCollection;
    }

    /**
     * Преобразование одной коллекции в другую
     * @param newTableCollection тип, который нужно произвести преобразование
     */
    public void setNewCollection(ContainerTableInterface newTableCollection) {
            tableCollection = newTableCollection.setCollection(tableCollection);
    }

    /**
     * Чтение файла
     * @return код состояния чтения файла
     */
    public int readFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String fileLine;

            /*
             * Чтения и запись строки в фабрику, представляющую какую-либо коллекцию
             */
            while ((fileLine = bufferedReader.readLine()) != null) {

                String[] fileLineArray = LineFormatter.parseLine(fileLine);
                /*
                 * Проверка на правильности входной строки.
                 * Если она не удовлетворяет условиям,
                 * то будет проигнорирована при записи
                 * в структуру данных
                 */
                if(LineFormatter.checkCellsCount(fileLineArray.length)
                        && LineFormatter.checkIdType(fileLineArray[0])
                        && LineFormatter.checkIdValue(fileLineArray[0])) {

                    tableCollection.add(new TableLine(Integer.parseInt(fileLineArray[0]),
                            Arrays.copyOfRange(fileLineArray, 1, fileLineArray.length)));

                }

            }

        } catch (FileNotFoundException e) {
            return FILE_NOT_FOUND;
        } catch (IOException e) {
            return IO_EXCEPTION;
        }

        return SUCCESS;
    }
    /**
     * Левостороннее объединение
     * @param toJoinTableCollection правая таблицв
     * @param tableLine тип табличного представления
     * @return полученная таблица
     */
    @Override
    public ContainerTableInterface doLeftOuterJoin(ContainerTableInterface toJoinTableCollection, TableLineInterface tableLine) {
        return tableCollection.doLeftOuterJoin(toJoinTableCollection, tableLine);
    }

    public ContainerTableInterface getTableCollection() {
        return tableCollection;
    }

    @Override
    public boolean isEmpty() {
        return tableCollection.isEmpty();
    }
}
