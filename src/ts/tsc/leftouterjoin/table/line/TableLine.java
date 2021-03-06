package ts.tsc.leftouterjoin.table.line;

import java.util.List;

public class TableLine implements TableLineInterface{
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
    public TableLine setParameters(List<Object> parameters) {
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
    public int getValuableCellsCount() {
        return values.length;
    }


    @Override
    public String[] getValues() {
        return values;
    }

    @Override
    public TableLineInterface getLine() {
        return this;
    }
}
