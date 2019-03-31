import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ts.tsc.leftouterjoin.table.line.LineComparator;
import ts.tsc.leftouterjoin.table.line.TableLineInterface;
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

    private List<TableLineInterface> sortedList;
    private List<TableLineInterface> toSortList;

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

        Iterator<TableLineInterface> toSortIterator = toSortList.iterator();
        Iterator<TableLineInterface> sortedIterator = sortedList.iterator();

        while (toSortIterator.hasNext() && sortedIterator.hasNext()) {
            TableLineInterface toSort = toSortIterator.next();
            TableLineInterface sorted = sortedIterator.next();

            assertThat(toSort.getId(), is(sorted.getId()));
            assertThat(toSort.getValues(), is(sorted.getValues()));
        }
    }
}
