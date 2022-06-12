package jpabasic;

import jpabasic.reserve.domain.User;
import jpabasic.reserve.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringDataJpa03Application implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpa03Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.update("truncate table user");

        User user1 = new User("email1@email.com", "이름1", LocalDateTime.now());
        userRepository.save(user1);
        logger.info("saved: {}", user1.getEmail());
        User user2 = new User("email2@email.com", "이름2", LocalDateTime.now());
        userRepository.save(user2);
        logger.info("saved: {}", user2.getEmail());

        // TODO
        // userRepository.findByNameLikeOrderByNameDesc()
    }
}
