package jpabasic;

import jpabasic.reserve.domain.User;
import jpabasic.reserve.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

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
        for (int i = 1 ; i <= 20 ; i++) {
            User user = new User("email" + i + "@email.com", "이름" + i, LocalDateTime.now());
            userRepository.save(user);
            logger.info("saved: {}", user.getEmail());
        }
        List<User> users = userRepository.findByNameLikeOrderByNameAscEmailDesc("이름%");
        logger.info("users: {}", users.size());

        Sort sort1 = Sort.by(Sort.Order.asc("name"));
        List<User> users1 = userRepository.findByNameLike("이름%", sort1);
        logger.info("users1: {}", users1.size());

        Sort sort2 = Sort.by(Sort.Order.asc("name"), Sort.Order.desc("email"));
        List<User> users2 = userRepository.findByNameLike("이름%", sort2);
        logger.info("users2: {}", users2.size());

        Sort sort3 = Sort.by(Sort.Order.asc("name"), Sort.Order.desc("email"));
        Pageable pageable = PageRequest.ofSize(10).withPage(1).withSort(sort3);
        List<User> users3 = userRepository.findByNameLike("이름%", pageable);
        logger.info("users3: {}", users3.size());

        Pageable pageable2 = PageRequest.ofSize(10).withPage(0).withSort(sort3);
        Page<User> page = userRepository.findByEmailLike("email%", pageable2);
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<User> content = page.getContent();
        int size = page.getSize();
        int pageNumber = page.getNumber();
        int numberOfElements = page.getNumberOfElements(); // content의 개수
        logger.info("totalElements: {}, totalPages: {}, size: {}, pageNumber: {}, numberOfElements: {}",
                totalElements, totalPages, size, pageNumber, numberOfElements);

        List<User> recentUsers1 = userRepository.findRecentUsers(LocalDateTime.now().minusDays(1));
        logger.info("recentUsers1: {}", recentUsers1.size());
        List<User> recentUsers2 = userRepository.findRecentUsers(LocalDateTime.now().minusDays(1), Sort.by("name"));
        logger.info("recentUsers2: {}", recentUsers2.size());
        Page<User> recentUsers3 = userRepository.findRecentUsers(LocalDateTime.now().minusDays(1), PageRequest.ofSize(10).withPage(0));
        logger.info("recentUsers3: {}", recentUsers3.getContent().size());
    }
}
