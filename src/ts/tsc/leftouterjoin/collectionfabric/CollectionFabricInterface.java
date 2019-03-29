package ts.tsc.leftouterjoin.collectionfabric;

import ts.tsc.leftouterjoin.table.TableInterface;
import ts.tsc.leftouterjoin.table.line.LineComparator;
import ts.tsc.leftouterjoin.table.line.LineInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;


/**
 * Интерфейс фабрик с основными методами,
 * которые должны быть реализованы в фабриках
 */
public interface CollectionFabricInterface extends TableInterface {
    void add(LineInterface stroke);
    CollectionFabricInterface setCollection(CollectionFabricInterface table);
    Stream<LineInterface> getTableStream();

    /**
     * Преобразование содержимого контейнеров в отсортированный массив строк
     * @return отсортированный массив строк
     */
    default String[] toStringArray() {
        return getTableStream()
                .sorted(new LineComparator())
                .map(LineInterface::toString)
                .toArray(String[]::new);
    }

    /**
     * Преобразование к Collection<LineInterface>
     * @param collector интерфейс, описывающий тип желаемой коллекции
     * @param <A> аккумулятор
     * @return запрошенный контейнер  в виде интерфейса Collection<LineInterface>
     */
    default <A> Collection<LineInterface> getCollection(Collector<? super LineInterface, A,
            Collection<LineInterface>> collector) {
        return getTableStream()
                .collect(collector);
    }

    /**
     * Преобразование к Map<Integer, List<LineInterface>>
     * @param collector интерфейс, описывающий тип желаемой коллекции
     * @param <A> аккумулятор
     * @return запрошенный контейнер  в виде интерфейса Map<Integer, List<LineInterface>>
     */
    default <A> Map<Integer, List<LineInterface>> getMap(Collector<? super LineInterface, A,
            Map<Integer, List<LineInterface>>> collector) {
        return getTableStream()
                .collect(collector);
    }
}
