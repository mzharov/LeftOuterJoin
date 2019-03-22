package ts.tsc.leftouterjoin.table;

import java.util.ArrayList;

public interface LineInterface {
    Integer getId();

    int getValuesSize();
    LineInterface getLine();
    String[] getValues();
    TableLine setParameters(ArrayList<Object> parameters);

    String toString();
}
