package ru.vsu.sheluhin.carService.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.vsu.sheluhin.carService.entity.Order;

import java.time.LocalDate;
import java.util.Optional;

public class OrderSpecifications {

    public static Specification<Order> hasStateNumber(Optional<String> stateNumber) {
        return (root, query, criteriaBuilder) ->
                stateNumber.map(value -> criteriaBuilder.equal(root.get("stateNumber"), value)).orElse(null);
    }

    public static Specification<Order> hasDateGreaterThanOrEqualTo(Optional<LocalDate> startDate) {
        return (root, query, criteriaBuilder) ->
                startDate.map(value -> criteriaBuilder.greaterThanOrEqualTo(root.get("visitDate"), value)).orElse(null);
    }

    public static Specification<Order> hasDateLessThanOrEqualTo(Optional<LocalDate> endDate) {
        return (root, query, criteriaBuilder) ->
                endDate.map(value -> criteriaBuilder.lessThanOrEqualTo(root.get("visitDate"), value)).orElse(null);
    }

    public static Specification<Order> hasUserId(int userId) {
        return (root, query, criteriaBuilder) -> {
            Predicate isNotNull = criteriaBuilder.isNotNull(root.get("userId"));
            Predicate isEqual = criteriaBuilder.equal(root.get("userId"), userId);
            return criteriaBuilder.and(isNotNull, isEqual);
        };
    }
}
