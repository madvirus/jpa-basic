package jpabasic.reserve.domain;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserNameSpecification implements Specification<User> {
    private final String value;

    public UserNameSpecification(String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("name"), "%" + value + "%");
    }
}
