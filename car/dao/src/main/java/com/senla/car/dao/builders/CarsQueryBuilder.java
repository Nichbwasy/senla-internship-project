package com.senla.car.dao.builders;

import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.car.model.Car;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class CarsQueryBuilder {

    public static List<Predicate> buildPredicatesForFilter(CriteriaBuilder builder, Root<Car> root, CarsCatalogFilterForm filterForm) {
        List<Predicate> predicates = new ArrayList<>();
        if(filterForm.getFilterByCondition()) {
            predicates.add(builder.equal(
                    root.get(Car_.condition).get(Condition_.name),
                    filterForm.getConditionName()
            ));
        }
        if(filterForm.getFilterByColor()) {
            predicates.add(builder.equal(
                    root.get(Car_.registration).get(Registration_.color).get(Color_.name),
                    filterForm.getColorName()
            ));
        }
        if(filterForm.getFilterByMileage()) {
            predicates.add(builder.between(
                    root.get(Car_.mileage),
                    filterForm.getMileageMin(),
                    filterForm.getMileageMax()
            ));
        }
        if(filterForm.getFilterByModel()) {
            predicates.add(builder.like(
                    root.get(Car_.registration).get(Registration_.model),
                    "%" + filterForm.getModelName() + "%"
            ));
        }
        if(filterForm.getFilterByStatus()) {
            predicates.add(builder.equal(
                    root.get(Car_.status).get(Status_.name),
                    filterForm.getStatusName()
            ));
        }
        if(filterForm.getFilterByType()) {
            predicates.add(builder.equal(
                    root.get(Car_.registration).get(Registration_.type).get(Type_.name),
                    filterForm.getTypeName()
            ));
        }
        if(filterForm.getFilterByReleaseYear()) {
            predicates.add(builder.between(
                    root.get(Car_.registration).get(Registration_.releaseYear),
                    filterForm.getReleaseYearMin(),
                    filterForm.getReleaseYearMax()
            ));
        }
        return predicates;
    }

    public static List<Order> buildOrdersForFilter(CriteriaBuilder builder, Root<Car> root, CarsCatalogFilterForm filterForm) {
        List<Order> orders = new ArrayList<>();
        if (filterForm.getEnableOrdering()) {
            filterForm.getOrderingFields()
                    .forEach(field -> {
                        Expression<?> expression = null;
                        switch (field.getFieldsName()) {
                            case TYPE -> expression = root.get(Car_.registration).get(Registration_.type).get(Type_.name);
                            case YEAR -> expression = root.get(Car_.registration).get(Registration_.releaseYear);
                            case MODEL -> expression = root.get(Car_.registration).get(Registration_.model);
                            case STATUS -> expression = root.get(Car_.status).get(Status_.name);
                            case CONDITION -> expression = root.get(Car_.condition).get(Condition_.name);
                        }
                        Order order = null;
                        switch (field.getOrderType()) {
                            case ASC -> order = builder.asc(expression);
                            case DESC -> order = builder.desc(expression);
                        }
                        orders.add(order);
                    });
        }
        return orders;
    }

}
