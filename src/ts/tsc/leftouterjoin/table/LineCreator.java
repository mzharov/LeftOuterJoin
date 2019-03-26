package ts.tsc.leftouterjoin.table;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для создания строк в табличном представлении
 */
public class LineCreator {
    /**
     * Создание экземпляра строки, для которой не было найдено соответствие в правой таблице
     * @param lineInterfaceLeft интерфейс табличного представления
     * @param size количество ячеек, которые не являются ключом
     * @return Список объектов дял добавления в таблицу
     */
    public static LinkedList<Object> createNotJoinedLine(LineInterface lineInterfaceLeft, int size) {
        LinkedList<Object> values = new LinkedList<>();
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
    public static List<Object> createLine(LineInterface lineTableFirst, LineInterface lineTableSecond) {
        List<Object> tableParameters = new LinkedList<>();
        tableParameters.add(lineTableFirst.getId());
        tableParameters.addAll(Arrays.asList(lineTableFirst.getValues()));
        tableParameters.addAll(Arrays.asList(lineTableSecond.getValues()));
        return tableParameters;
    }
}
