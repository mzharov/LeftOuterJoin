package ts.tsc.leftouterjoin.table;

/**
 * Парсер строки
 */
public class LineFormatter {
    /**
     * Преобразование входной строки в массив значений
     * @return массив строк со значениями ячеек строки таблицы
     */
    public static String[] parseLine(String fileLine) {
        return (fileLine.split("\\s+"));
    }
}
