package tsc.ts.leftouterjoin.table;

public class Stroke implements StrokeInterface{
    private Integer id;
    private String value;
    public Stroke(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public StrokeInterface getStroke() {
        return null;
    }
}
