package jpabasic.reserve.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    void save(User user);

    void delete(User user);
}
