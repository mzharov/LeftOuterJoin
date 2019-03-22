package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.LineInterface;

import java.util.*;

public class ListFabricLinked extends ListFabric {

    public ListFabricLinked() {
        super(new LinkedList<>());
    }

    class ListComparator implements Comparator<LineInterface> {
        @Override
        public int compare(LineInterface firstElement, LineInterface secondElement) {
            return firstElement.getId().compareTo(secondElement.getId());
        }
    }

    private ArrayList<Object> createLine(LineInterface lineTable) {
        ArrayList<Object> tableParameters = new ArrayList<>();
        tableParameters.add(lineTable.getId());
        tableParameters.addAll(Arrays.asList(lineTable.getValues()));
        tableParameters.addAll(Arrays.asList(lineTable));
        return tableParameters;
    }
    @Override
    public CollectionFabricInterface doLeftOuterJoin(CollectionFabricInterface toJoinTableCollection, LineInterface tableLine){
        listTable.sort(new ListComparator());

        ListFabric requestedTableCollection = new ListFabric(new ArrayList<>());

        for (LineInterface lineInterfaceLeft : listTable) {

            LinkedList<LineInterface> tableLinkedList= (LinkedList<LineInterface>) toJoinTableCollection.getListCollection();
            Iterator<LineInterface> frontIterator =
                    tableLinkedList.iterator();
            Iterator<LineInterface> backwardIterator =
                    tableLinkedList.descendingIterator();

            boolean idFound = false;
            int size = toJoinTableCollection.getListCollection().get(0).getValuesSize();


            if(lineInterfaceLeft.getId().compareTo(tableLinkedList.peek().getId()) < 0
                    || lineInterfaceLeft.getId().compareTo(tableLinkedList.getLast().getId()) > 0) {
                continue;
            }
            while (!frontIterator.equals(backwardIterator)
                    && frontIterator.hasNext()
                    && backwardIterator.hasNext()) {
                System.out.println("xx");
                LineInterface head = frontIterator.next();
                LineInterface end = backwardIterator.next();
                if(lineInterfaceLeft.getId().compareTo(head.getId())== 0) {
                    idFound = true;
                    requestedTableCollection.add(tableLine.setParameters(createLine(head)));
                }
                if(lineInterfaceLeft.getId().compareTo(end.getId())== 0) {
                    idFound = true;
                    requestedTableCollection.add(tableLine.setParameters(createLine(end)));
                }

                if(!idFound) {
                    requestedTableCollection.add(tableLine
                            .setParameters(getNotJoinedLine(lineInterfaceLeft, size)));
                }
            }

        }
        return requestedTableCollection;
    }
}
