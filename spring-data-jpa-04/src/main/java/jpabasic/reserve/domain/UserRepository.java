package jpabasic.reserve.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, String> {
    void save(User u);

    List<User> findAll(Specification<User> spec);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
