package ts.tsc.leftouterjoin.collectionfabric.lists;

import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.Comparator;

class ListComparator implements Comparator<LineInterface> {
    @Override
    public int compare(LineInterface firstElement, LineInterface secondElement) {
        return firstElement.getId().compareTo(secondElement.getId());
    }
}
