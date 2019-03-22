package ts.tsc.leftouterjoin.table;

public class TableLine implements LineInterface{
    private Integer id;
    private String value;
    public TableLine(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return id + " " + value;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public LineInterface getLine() {
        return this;
    }
}
