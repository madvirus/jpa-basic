package jpabasic.reserve.domain;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserSpecs {
    public static Specification<User> nameLike(String value) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + value + "%");
    }

    public static Specification<User> createdAfter(LocalDateTime dt) {
        return (root, query, cb) -> cb.greaterThan(root.get("createDate"), dt);
    }
}
