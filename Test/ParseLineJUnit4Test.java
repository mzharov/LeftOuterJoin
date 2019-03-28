import org.junit.Assert;
import org.junit.Test;
import ts.tsc.leftouterjoin.table.line.LineFormatter;

public class ParseLineJUnit4Test extends Assert {

    @Test
    public void testParseLine() {
        assertArrayEquals(new String[]{"1","2","3","4"}, LineFormatter.parseLine("1            2 3       4"));
    }
}
