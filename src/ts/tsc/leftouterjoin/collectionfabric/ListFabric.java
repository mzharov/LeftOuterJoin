package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListFabric implements CollectionFabricInterface {
    protected List<LineInterface> listTable;
    public ListFabric(List<LineInterface> listTable) {
        this.listTable = listTable;
    }

    @Override
    public CollectionFabricInterface getCollection(CollectionFabricInterface collectionFabricTable) {
        return null;
    }

    protected ArrayList<Object> getNotJoinedLine(LineInterface lineInterfaceLeft, int size) {
        ArrayList<Object> values = new ArrayList<>();
        values.add(lineInterfaceLeft.getId());
        values.addAll(Arrays.asList(lineInterfaceLeft.getValues()));
        for(int iterator = 0; iterator < size; iterator++) {
            values.add("null");
        }
        return values;
    }
    @Override
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine){

        ListFabric requestedTableCollection = new ListFabric(new ArrayList<>());

        for (LineInterface lineInterfaceLeft : listTable) {
            boolean idFound = false;
            int size = toJoinTableCollection.getListCollection().get(0).getValuesSize();

            for (LineInterface toJoinIterator : toJoinTableCollection.getListCollection()) {
                if(lineInterfaceLeft.getId().compareTo(toJoinIterator.getId())==0) {
                    ArrayList<Object> tableParameters = new ArrayList<>();
                    tableParameters.add(lineInterfaceLeft.getId());
                    tableParameters.addAll(Arrays.asList(lineInterfaceLeft.getValues()));
                    tableParameters.addAll(Arrays.asList(toJoinIterator.getValues()));

                    requestedTableCollection.add(tableLine.setParameters(tableParameters));
                    idFound = true;
                }
            }

            if(!idFound) {
                requestedTableCollection.add(tableLine
                        .setParameters(getNotJoinedLine(lineInterfaceLeft, size)));
            }

        }

        return requestedTableCollection;
    }

    @Override
    public boolean isEmpty() {
        return listTable.isEmpty();
    }

    @Override
    public String[] printTable() {
        return listTable.stream()
                .map(LineInterface::toString)
                .toArray(String[]::new);
    }
    @Override
    public String toString() {
        return null;
    }

    @Override
    public void add(LineInterface stroke) {
        listTable.add(stroke);
    }

    @Override
    public List<LineInterface> getListCollection() {
        return listTable;
    }

    @Override
    public Map<Integer, LineInterface> getMapCollection() {
        /*
         * TODO
         */
        return null;
    }
}
