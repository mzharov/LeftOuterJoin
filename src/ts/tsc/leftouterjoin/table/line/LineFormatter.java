package ts.tsc.leftouterjoin.table.line;

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

    /**
     * проверка количества элементов во входной строке
     * @param cellsCount количество элементов в массиве, хранящем строку
     * @return true - если элементов больше 1 (id и хотя бы одно значение), иначе - false
     */
    public static boolean checkCellsCount(int cellsCount) {
        return cellsCount > 1;
    }

    /**
     * Проверка id
     * @param id индентификтор строки
     * @return true - если значение >=0, иначе false
     */
    public static boolean checkIdValue(String id) {
        return Integer.parseInt(id) >=0;
    }

    /**
     * Проверка типа идентификаторы
     * @param id идентификатор строки
     * @return true - если удалось преобразовать элемент массива, хранящего идентификатор в тип int
     */
    public static boolean checkIdType(String id) {
        try {
            Integer.parseInt(id);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
