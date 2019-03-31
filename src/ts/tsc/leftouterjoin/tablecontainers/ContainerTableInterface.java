package ts.tsc.leftouterjoin.tablecontainers;

import ts.tsc.leftouterjoin.table.TableInterface;
import ts.tsc.leftouterjoin.table.line.LineComparator;
import ts.tsc.leftouterjoin.table.line.TableLineInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;


/**
 * Интерфейс фабрик с основными методами,
 * которые должны быть реализованы в фабриках
 */
public interface ContainerTableInterface extends TableInterface {
    void add(TableLineInterface stroke);
    ContainerTableInterface setCollection(ContainerTableInterface table);
    Stream<TableLineInterface> getTableStream();

    /**
     * Преобразование содержимого контейнеров в отсортированный массив строк
     * @return отсортированный массив строк
     */
    default String[] toStringArray() {
        return getTableStream()
                .sorted(new LineComparator())
                .map(TableLineInterface::toString)
                .toArray(String[]::new);
    }

    /**
     * Преобразование к Collection<TableLineInterface>
     * @param collector интерфейс, описывающий тип желаемой коллекции
     * @param <A> аккумулятор
     * @return запрошенный контейнер  в виде интерфейса Collection<TableLineInterface>
     */
    default <A> Collection<TableLineInterface> getCollection(Collector<? super TableLineInterface, A,
            Collection<TableLineInterface>> collector) {
        return getTableStream()
                .collect(collector);
    }

    /**
     * Преобразование к Map<Integer, List<TableLineInterface>>
     * @param collector интерфейс, описывающий тип желаемой коллекции
     * @param <A> аккумулятор
     * @return запрошенный контейнер  в виде интерфейса Map<Integer, List<TableLineInterface>>
     */
    default <A> Map<Integer, List<TableLineInterface>> getMap(Collector<? super TableLineInterface, A,
            Map<Integer, List<TableLineInterface>>> collector) {
        return getTableStream()
                .collect(collector);
    }
}
