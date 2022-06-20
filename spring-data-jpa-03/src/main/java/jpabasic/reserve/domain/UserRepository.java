package jpabasic.reserve.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    User save(User user);

    void delete(User user);

    List<User> findByNameLike(String keyword);

    List<User> findByNameLikeOrderByNameDesc(String keyword);

    List<User> findByNameLikeOrderByNameAscEmailDesc(String keyword);

    List<User> findByNameLike(String keyword, Sort sort);

    List<User> findByNameLike(String keyword, Pageable pageable);

    Page<User> findByEmailLike(String keyword, Pageable pageable);

    @Query("select u from User u where u.createDate > :since order by u.createDate desc")
    List<User> findRecentUsers(@Param("since") LocalDateTime since);

    @Query("select u from User u where u.createDate > :since")
    List<User> findRecentUsers(@Param("since") LocalDateTime since, Sort sort);

    @Query("select u from User u where u.createDate > :since")
    Page<User> findRecentUsers(@Param("since") LocalDateTime since, Pageable pageable);
}
