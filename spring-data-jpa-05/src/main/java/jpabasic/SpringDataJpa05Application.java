package jpabasic;

import jpabasic.reserve.domain.User;
import jpabasic.reserve.domain.UserRepository;
import jpabasic.reserve.domain.UserSpecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringDataJpa05Application implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(SpringDataJpa05Application.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpa05Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.update("truncate table user");
        for (int i = 1 ; i <= 20 ; i++) {
            User user = new User("email" + i + "@email.com", "이름" + i, LocalDateTime.now());
            userRepository.save(user);
            logger.info("saved: {}", user.getEmail());
        }
        User user = new User("email21@email.com", "이름20", LocalDateTime.now());
        userRepository.save(user);

        long count = userRepository.countByNameLike("이름1%");
        logger.info("count: {}", count);
        long count2 = userRepository.count(UserSpecs.nameLike("이름"));
        logger.info("count2: {}", count2);
        List<User> users = userRepository.findRecentUsers();
        logger.info("users count: {}", users.size());
        users.forEach(u -> logger.info("u: {}", u));

        logger.info("max createDate: {}", userRepository.selectLastCreateDate());

        Optional<User> user1 = userRepository.findByName("이름1");
        user1.ifPresentOrElse(u -> logger.info("이름1: {}", u), () -> logger.info("이름1: 없음"));
        Optional<User> user0 = userRepository.findByName("이름0");
        user0.ifPresentOrElse(u -> logger.info("이름0: {}", u), () -> logger.info("이름0: 없음"));
        // Optional<User> user20 = userRepository.findByName("이름20"); // name이 '이름20'이 데이터가 두 개 존재하므로 익셉션
    }
}
