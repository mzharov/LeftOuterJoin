package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс для создания строк в табличном представлении
 */
class LineCreator {
    /**
     * Создание экземпляра строки, для которой не было найдено соответствие в правой таблице
     * @param lineInterfaceLeft интерфейс табличного представления
     * @param size количество ячеек, которые не являются ключом
     * @return Список объектов дял добавления в таблицу
     */
    public static ArrayList<Object> getNotJoinedLine(LineInterface lineInterfaceLeft, int size) {
        ArrayList<Object> values = new ArrayList<>();
        values.add(lineInterfaceLeft.getId());
        values.addAll(Arrays.asList(lineInterfaceLeft.getValues()));
        for(int iterator = 0; iterator < size; iterator++) {
            values.add("null");
        }
        return values;
    }

    /**
     * Создание экземпляра строки, для которой было найдено соотвествие в правой таблице
     * @param lineTableFirst интерфейс лвой таблицы
     * @param lineTableSecond интерфейс право таблицы
     * @return Список объектов дял добавления в таблицу
     */
    public static ArrayList<Object> createLine(LineInterface lineTableFirst, LineInterface lineTableSecond) {
        ArrayList<Object> tableParameters = new ArrayList<>();
        tableParameters.add(lineTableFirst.getId());
        tableParameters.addAll(Arrays.asList(lineTableFirst.getValues()));
        tableParameters.addAll(Arrays.asList(lineTableSecond.getValues()));
        return tableParameters;
    }
}
