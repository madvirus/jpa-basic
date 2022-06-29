package jpabasic.reserve.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    void save(User u);

    long countByNameLike(String keyword);

    long count(Specification<User> spec);

    @Query(
            value = "select * from user u where u.create_date >= date_sub(now(), interval 1 day)",
            nativeQuery = true)
    List<User> findRecentUsers();

    @Query(
            value = "select max(create_date) from user",
            nativeQuery = true)
    LocalDateTime selectLastCreateDate();

    Optional<User> findByName(String name);
}
