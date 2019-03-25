package ts.tsc.leftouterjoin.table;

import java.util.ArrayList;

public class TableLine implements LineInterface{
    private Integer id;
    private String[] values;
    public TableLine(Integer id, String[] values) {
        this.id = id;
        this.values = values;
    }
    public TableLine() {}

    /**
     * задание параметров строки с помощью списка объектов
     * @param parameters список объектов для инициализации строки
     * @return строка TableLine
     */
    @Override
    public TableLine setParameters(ArrayList<Object> parameters) {
        String[] values = new String[parameters.size()-1];

        for(int iterator = 1; iterator < parameters.size(); iterator++) {
            values[iterator-1] = parameters.get(iterator).toString();
        }
        return new TableLine((Integer) parameters.get(0), values);
    }

    @Override
    public String toString() {
        StringBuilder tableLine = new StringBuilder();

        tableLine.append(id);
        for (String line : values) {
            tableLine.append(" ");
            tableLine.append(line);
        }
        return tableLine.toString();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int getValuesSize() {
        return values.length;
    }


    @Override
    public String[] getValues() {
        return values;
    }
}
