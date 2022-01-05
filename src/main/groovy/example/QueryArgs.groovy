package example

import grails.validation.Validateable
import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import org.grails.datastore.mapping.query.Query

@Builder
@CompileStatic
class QueryArgs implements Validateable {
    Query.Order order
    Integer max
    Integer offset

    QueryArgs() {

    }

    QueryArgs(Integer max,
              Integer offset,
              String sortPropertyName,
              String direction) {
        this(max, offset, sortPropertyName, directionOf(direction));
    }

    QueryArgs(Integer max,
              Integer offset,
              String sortPropertyName,
              Query.Order.Direction direction) {
        this(max, offset, sortPropertyName ? new Query.Order(sortPropertyName, direction) : null);
    }

    QueryArgs(Integer max,
              Integer offset,
              Query.Order order) {
        this.max = max;
        this.offset = offset;
        this.order = order;
    }

    Optional<Integer> getMax() {
        Optional.ofNullable(max)
    }

    Optional<Integer> getOffset() {
        Optional.ofNullable(offset)
    }

    Optional<Query.Order> getOrder() {
        Optional.ofNullable(order)
    }

    static Query.Order orderOf(String sortPropertyName,
                          String direction) {
        sortPropertyName ? new Query.Order(sortPropertyName, directionOf(direction)) : null
    }

    static Query.Order.Direction directionOf(String direction) {
        "desc".equalsIgnoreCase(direction) ? Query.Order.Direction.DESC : Query.Order.Direction.ASC
    }


    @Override
    String toString() {
        return "QueryArgs{" +
                "order=" + order +
                ", max=" + max +
                ", offset=" + offset +
                '}';
    }
}
