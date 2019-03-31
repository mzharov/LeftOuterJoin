package ts.tsc.leftouterjoin.table.line;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для создания строк в табличном представлении
 */
public class LineCreator {
    /**
     * Создание экземпляра строки, для которой не было найдено соответствие в правой таблице
     * @param TableLineInterfaceLeft интерфейс табличного представления
     * @param size количество ячеек, которые не являются ключом
     * @return Список объектов дял добавления в таблицу
     */
    public static LinkedList<Object> createNotJoinedLine(TableLineInterface TableLineInterfaceLeft, int size) {
        LinkedList<Object> values = new LinkedList<>();
        values.add(TableLineInterfaceLeft.getId());
        values.addAll(Arrays.asList(TableLineInterfaceLeft.getValues()));
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
    public static List<Object> createLine(TableLineInterface lineTableFirst, TableLineInterface lineTableSecond) {
        List<Object> TableTableLineInterface = new LinkedList<>();
        TableTableLineInterface.add(lineTableFirst.getId());
        TableTableLineInterface.addAll(Arrays.asList(lineTableFirst.getValues()));
        TableTableLineInterface.addAll(Arrays.asList(lineTableSecond.getValues()));
        return TableTableLineInterface;
    }
}
