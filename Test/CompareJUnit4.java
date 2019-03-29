import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ts.tsc.leftouterjoin.table.line.LineComparator;
import ts.tsc.leftouterjoin.table.line.LineInterface;
import ts.tsc.leftouterjoin.table.line.TableLine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Проверка правильности сравнения двух строк таблицы
 */
public class CompareJUnit4 extends Assert {

    private List<LineInterface> sortedList;
    private List<LineInterface> toSortList;

    @Before
    public void setUp() {
        toSortList = new LinkedList<>(
                Arrays.asList(new TableLine(1, new String[]{"A, A"}),
                        new TableLine(2, new String[]{"A, B"}),
                        new TableLine(2, new String[]{"A, B"}),
                        new TableLine(1, new String[]{"A, B"})));
        toSortList.sort(new LineComparator());

        sortedList = new LinkedList<>(
                Arrays.asList(new TableLine(1, new String[]{"A, A"}),
                        new TableLine(1, new String[]{"A, B"}),
                        new TableLine(2, new String[]{"A, B"}),
                        new TableLine(2, new String[]{"A, B"})));
    }

    @Test
    public void testLineComparator() {

        Iterator<LineInterface> toSortIterator = toSortList.iterator();
        Iterator<LineInterface> sortedIterator = sortedList.iterator();

        while (toSortIterator.hasNext() && sortedIterator.hasNext()) {
            LineInterface toSort = toSortIterator.next();
            LineInterface sorted = sortedIterator.next();

            assertThat(toSort.getId(), is(sorted.getId()));
            assertThat(toSort.getValues(), is(sorted.getValues()));
        }
    }
}
