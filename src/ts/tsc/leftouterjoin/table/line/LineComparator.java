package ts.tsc.leftouterjoin.table.line;

import java.util.Comparator;

/**
 * Компаратор LineInterface для сравнения по ключу
 */
public class LineComparator implements Comparator<LineInterface> {

    /**
     * Сравнение массивов строковых значений
     * @param firstValues значения первой строки
     * @param secondValues значения второй строки
     * @return 0, если значения полностью идентичны; иначе зависит от
     * от сравнения знаечний:
     * если они равны, сравниваются все последующие
     */
    private static int compareValues(final String[] firstValues, final String[] secondValues) {
        for(int iterator = 0; iterator < firstValues.length; iterator++) {
            int valueCompare = firstValues[iterator].compareTo(secondValues[iterator]);
            if(!(valueCompare == 0)) {
                return valueCompare;
            }
        }
        return 0;
    }

    private static int compareKeys(Integer firstKey, Integer secondKey) {
        int keyCompare = firstKey.compareTo(secondKey);
        if (keyCompare == 0) {
            return 0;
        } else {
            return keyCompare;
        }
    }

    /**
     * Сравнение двух строк LineInterface:
     * сначала по ключу, затем по значениям, если ключи одинаковые
     * @param firstElement первая строка
     * @param secondElement вторая строка
     * @return 0, если входные строк полностью идентичны, иначе зависит от сравнения ключей и значений
     */
    @Override
    public int compare(LineInterface firstElement, LineInterface secondElement) {
        int compare = compareKeys(firstElement.getId(), secondElement.getId());
        if(compare == 0) {
            return compareValues(firstElement.getValues(), secondElement.getValues());
        } else {
            return  compare;
        }
    }
}
