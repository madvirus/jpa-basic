package jpabasic;

import jpabasic.reserve.domain.User;
import jpabasic.reserve.domain.UserNameSpecification;
import jpabasic.reserve.domain.UserRepository;
import jpabasic.reserve.domain.UserSpecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringDataJpa04Application implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(SpringDataJpa04Application.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpa04Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.update("truncate table user");
        for (int i = 1 ; i <= 20 ; i++) {
            User user = new User("email" + i + "@email.com", "이름" + i, LocalDateTime.now());
            userRepository.save(user);
            logger.info("saved: {}", user.getEmail());
        }

        UserNameSpecification spec = new UserNameSpecification("이름");
        List<User> users = userRepository.findAll(spec);
        users.forEach(u -> {
            logger.info("user name like %이름% : {}", u.getEmail());
        });

        Specification<User> nameSpec = UserSpecs.nameLike("이름1");
        Specification<User> afterSpec = UserSpecs.createdAfter(LocalDateTime.now().minusHours(1));
        Specification<User> compositespec = nameSpec.and(afterSpec);
        List<User> users2 = userRepository.findAll(compositespec);
        logger.info("users2 : {}", users2.size());

        Specification<User> spec3 = UserSpecs.nameLike("이름2")
                .and(UserSpecs.createdAfter(LocalDateTime.now().minusHours(1)));
        List<User> users3 = userRepository.findAll(spec3);
        logger.info("users3 : {}", users3.size());

        Page<User> pageUsers = userRepository.findAll(spec3, PageRequest.of(0, 3));

        logger.info("page.numberOfElements", pageUsers.getNumberOfElements());

        String keyword = "";
        LocalDateTime dt = LocalDateTime.now().minusMinutes(1);
        Specification<User> specs = SpecBuilder.builder(User.class)
                .ifHasText(keyword, str -> UserSpecs.nameLike(str))
                .ifNotNull(dt, value -> UserSpecs.createdAfter(value))
                .toSpec();

        List<User> users4 = userRepository.findAll(specs);
        logger.info("users4 : {}", users3.size());
    }
}
