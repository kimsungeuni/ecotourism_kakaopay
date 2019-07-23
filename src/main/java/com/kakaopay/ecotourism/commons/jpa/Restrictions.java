package com.kakaopay.ecotourism.commons.jpa;

import lombok.Data;
import org.hibernate.validator.internal.engine.groups.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Restrictions {

    private List<Condition> conditions = new ArrayList<Condition>();
    private List<Restrictions> children = new ArrayList<Restrictions>();
    private Conn currCon;

    public Restrictions() {
        this.currCon = Conn.AND;
    }

    public Restrictions(Conn conn) {
        this.currCon = conn;
    }

    public void clear() {
        this.conditions.clear();
    }

    public void addChild(Restrictions child) {
        this.children.add(child);
    }

    public Restrictions eq(String name, Object value) {
        conditions.add(new Condition(ConditionType.EQUALS, name, value));
        return this;
    }

    public Restrictions le(String name, Object value) {
        conditions.add(new Condition(ConditionType.LESS_THAN_OR_EQUAL_TO, name, value));
        return this;
    }

    public Restrictions ge(String name, Object value) {
        conditions.add(new Condition(ConditionType.GREATER_THAN_OR_EQUAL_TO, name, value));
        return this;
    }

    public Restrictions isNull(String key) {
        conditions.add(new Condition(ConditionType.IS_NULL, key));
        return this;
    }

    public Restrictions isNotNull(String key) {
        conditions.add(new Condition(ConditionType.IS_NOT_NULL, key));
        return this;
    }

    public Restrictions ne(String key, Object object) {
        conditions.add(new Condition(ConditionType.NOT_EQUAL, key, object));
        return this;
    }

    public Restrictions like(String key, String object) {
        conditions.add(new Condition(ConditionType.LIKE, key, object));
        return this;
    }

    public Restrictions between(String key, Object start, Object end) {
        conditions.add(new Condition(ConditionType.BETWEEN, key, start, end));
        return this;
    }

    @SuppressWarnings("rawtypes")
    public void in(String name, Collection value) {
        conditions.add(new Condition(ConditionType.IN, name, value));
    }

    @SuppressWarnings("rawtypes")
    public void notIn(String name, Collection value) {
        conditions.add(new Condition(ConditionType.NOT_IN, name, value));
    }

    public void isNullOrLe(String key, Object value) {
        conditions.add(new Condition(ConditionType.LESS_THAN_OR_IS_NULL, key, value));
    }

    public void isNullOrGe(String key, Object value) {
        conditions.add(new Condition(ConditionType.GREATER_THAN_OR_IS_NULL, key, value));
    }


    public void eqProperty(String key, String key2) {
        conditions.add(new Condition(ConditionType.EQUAL_PROPERTY, key, key2));
    }

    @SuppressWarnings("serial")
    public <T> Specification<T> output() {

        Specification<T> spec = new Specification<T>() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> items = new ArrayList<Predicate>();
                for (Condition condition : conditions) {

                    String key = condition.getName();
                    Object object = condition.getValue1();
                    Object object2 = condition.getValue2();

                    switch (condition.type) {
                        case EQUALS: {
                            items.add(cb.equal(getPath(root, key), object));
                            break;
                        }
                        case EQUAL_PROPERTY: {
                            items.add(cb.equal(getPath(root, key), getPath(root, object.toString())));
                            break;
                        }
                        case NOT_EQUAL: {
                            items.add(cb.notEqual(getPath(root, key), object));
                            break;
                        }
                        case LIKE: {
                            items.add(cb.like(getPath(root, key), (String) object));
                            break;
                        }
                        case IS_NULL: {
                            items.add(cb.isNull(getPath(root, key)));
                            break;
                        }
                        case IS_NOT_NULL: {
                            items.add(cb.isNotNull(getPath(root, key)));
                            break;
                        }
                        case IN: {
                            final Path<Group> group = getPath(root, key);
                            items.add(group.in(object));
                            break;
                        }
                        case NOT_IN: {
                            final Path<Group> group = getPath(root, key);
                            items.add(group.in((Collection) object).not());
                            break;
                        }
                        case LESS_THAN_OR_EQUAL_TO: {
                            if (object instanceof Date) {
                                items.add(cb.lessThanOrEqualTo(Restrictions.<Date>getPath(root, key), (Date) object));
                            } else if (LocalDate.class.isAssignableFrom(object.getClass())) {
                                items.add(cb.lessThanOrEqualTo(Restrictions.<LocalDate>getPath(root, key), (LocalDate) object));
                            } else {
                                items.add(cb.le(Restrictions.<Number>getPath(root, key), (Number) object));
                            }
                            break;
                        }
                        case GREATER_THAN_OR_EQUAL_TO: {
                            if (object instanceof Date) {
                                items.add(cb.greaterThanOrEqualTo(Restrictions.<Date>getPath(root, key), (Date) object));
                            } else if (LocalDate.class.isAssignableFrom(object.getClass())) {
                                items.add(cb.greaterThanOrEqualTo(Restrictions.<LocalDate>getPath(root, key), (LocalDate) object));
                            } else {
                                items.add(cb.ge(Restrictions.<Number>getPath(root, key), (Number) object));
                            }
                            break;
                        }
                        case LESS_THAN_OR_IS_NULL: {
                            Path path = getPath(root, key);
                            if (object instanceof Date) {
                                items.add(cb.or(cb.isNull(path), cb.lessThanOrEqualTo(path, (Date) object)));
                            } else if (LocalDate.class.isAssignableFrom(object.getClass())) {
                                items.add(cb.or(cb.isNull(path), cb.lessThanOrEqualTo(path, (LocalDate) object)));
                            } else {
                                items.add(cb.or(cb.isNull(path), cb.le(path, (Number) object)));
                            }
                            break;
                        }
                        case GREATER_THAN_OR_IS_NULL: {
                            Path path = getPath(root, key);
                            if (object instanceof Date) {
                                items.add(cb.or(cb.isNull(path), cb.greaterThanOrEqualTo(path, (Date) object)));
                            } else if (LocalDate.class.isAssignableFrom(object.getClass())) {
                                items.add(cb.or(cb.isNull(path), cb.greaterThanOrEqualTo(path, (LocalDate) object)));
                            } else {
                                items.add(cb.or(cb.isNull(path), cb.ge(path, (Number) object)));
                            }
                            break;
                        }
                        case BETWEEN: {
                            if (object instanceof Date) {
                                items.add(cb.between(Restrictions.<Date>getPath(root, key), (Date) object, (Date) object2));
                            } else if (LocalDate.class.isAssignableFrom(object.getClass())) {
                                items.add(cb.between(Restrictions.getPath(root, key), (LocalDate) object, (LocalDate) object2));
                            } else if (Long.class.isAssignableFrom(object.getClass())) {
                                items.add(cb.between(Restrictions.getPath(root, key), (Long) object, (Long) object2));
                            } else {
                                items.add(cb.between(Restrictions.getPath(root, key), (Double) object, (Double) object2));
                            }
                            break;
                        }

                        default:
                            break;
                    }

                }

                if (items.size() > 1) {
                    Predicate[] ps = items.toArray(new Predicate[]{});
                    return (currCon == Conn.AND) ? cb.and(ps) : cb.or(ps);
                } else if (items.size() == 1) {
                    return items.get(0);
                }

                return null;
            }
        };

        if (children.size() > 0) {
            for (Restrictions child : children) {
                spec = (currCon == Conn.AND) ? spec.and(child.output()) : spec.or(child.output());
            }
        }

        return spec;

    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <Y> Path<Y> getPath(Root root, String key) {
        Path<Y> x = null;
        if (key.contains(".")) {
            for (String k : key.split("\\.")) {
                x = (x == null ? root.get(k) : x.get(k));
            }
        } else {
            x = root.<Y>get(key);
        }
        return x;
    }

    @Data
    private class Condition {

        private ConditionType type;
        private String name;
        private Object value1;
        private Object value2;

        public Condition(ConditionType type, String name) {
            this(type, name, null);
        }

        public Condition(ConditionType type, String name, Object value1) {
            this.type = type;
            this.name = name;
            this.value1 = value1;
        }

        public Condition(ConditionType type, String name, Object value1, Object value2) {
            this.type = type;
            this.name = name;
            this.value1 = value1;
            this.value2 = value2;
        }

    }

    private enum ConditionType {
        EQUALS,
        IN,
        NOT_IN,
        LESS_THAN_OR_EQUAL_TO,
        GREATER_THAN_OR_EQUAL_TO,
        IS_NULL,
        IS_NOT_NULL,
        NOT_EQUAL,
        LIKE,
        BETWEEN,
        LESS_THAN_OR_IS_NULL,
        GREATER_THAN_OR_IS_NULL,
        EQUAL_PROPERTY,
    }

    public enum Conn {
        AND,
        OR
    }


}
