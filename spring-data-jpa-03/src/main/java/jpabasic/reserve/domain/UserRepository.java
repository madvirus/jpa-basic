package jpabasic.reserve.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    User save(User user);

    void delete(User user);

    List<User> findByNameLike(String keyworkd);
}
