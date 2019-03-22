package ts.tsc.leftouterjoin.table;

public class LineFormatter {
    public static String[] parseLine(String fileLine) {
        return (fileLine.split("\\s+"));
    }
}
