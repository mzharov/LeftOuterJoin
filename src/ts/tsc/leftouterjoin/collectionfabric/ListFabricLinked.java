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

            int size = toJoinTableCollection.getListCollection().get(0).getValuesSize();

            if(tableLinkedList.peek() == null) continue;
            else {
                int id = tableLinkedList.peek().getId();
                if(lineInterfaceLeft.getId().compareTo(id) < 0
                        || lineInterfaceLeft.getId().compareTo(tableLinkedList.getLast().getId()) > 0) {
                    requestedTableCollection.add(tableLine
                            .setParameters(getNotJoinedLine(lineInterfaceLeft, size)));
                    continue;
                }
            }
            while (frontIterator.hasNext() && backwardIterator.hasNext()) {

                LineInterface head = frontIterator.next();
                if(lineInterfaceLeft.getId().compareTo(head.getId())== 0) {
                    requestedTableCollection.add(tableLine.setParameters(createLine(lineInterfaceLeft, head)));
                }

                LineInterface end = backwardIterator.next();
                if (head.equals(end)) break;
                if(lineInterfaceLeft.getId().compareTo(end.getId())== 0) {
                    requestedTableCollection.add(tableLine.setParameters(createLine(lineInterfaceLeft, end)));
                }
            }
        }
        return requestedTableCollection;
    }
}
