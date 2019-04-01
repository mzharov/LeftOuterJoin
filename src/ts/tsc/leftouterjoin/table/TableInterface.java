package ts.tsc.leftouterjoin.table;

import ts.tsc.leftouterjoin.table.line.TableLineInterface;
import ts.tsc.leftouterjoin.tablecontainers.ContainerTableInterface;

public interface TableInterface {
    ContainerTableInterface doLeftOuterJoin(ContainerTableInterface toJoinTableCollection,
                                            TableLineInterface tableLine);
    boolean isEmpty();
}
