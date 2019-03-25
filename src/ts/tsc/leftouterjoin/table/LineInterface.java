package ts.tsc.leftouterjoin.table;

import java.util.ArrayList;

/**
 * Интерфейс строки таблицы
 */
public interface LineInterface {
    Integer getId();
    int getValueCellsCount();
    String[] getValues();
    TableLine setParameters(ArrayList<Object> parameters);
    String toString();
}
