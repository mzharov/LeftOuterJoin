package ts.tsc.leftouterjoin.table.line;

import java.util.List;

/**
 * Интерфейс строки таблицы
 */
public interface LineInterface {
    Integer getId();
    int getValuableCellsCount();
    String[] getValues();
    TableLine setParameters(List<Object> parameters);
    String toString();
}
