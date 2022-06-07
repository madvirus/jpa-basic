package jpabasic;

import jpabasic.reserve.app.*;
import jpabasic.reserve.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class SpringDataJpa01Application implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewUserService newUserService;
    @Autowired
    private GetUserService getUserService;
    @Autowired
    private ChangeNameService changeNameService;
    @Autowired
    private RemoveUserService removeUserService;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpa01Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("명령어를 입력하세요:");
                String line = reader.readLine();
                if (line == null) break;
                if (line.startsWith("new ")) {
                    handleNew(line);
                } else if (line.startsWith("get ")) {
                    handleGet(line);
                } else if (line.startsWith("change name ")) {
                    handleChangeName(line);
                } else if (line.startsWith("remove ")) {
                    handleRemove(line);
                } else if (line.equals("exit")) {
                    break;
                }
            }
        }
    }

    private void handleNew(String line) {
        String[] v = line.substring(4).split(" ");
        SaveRequest request = new SaveRequest(v[0], v[1]);
        try {
            newUserService.saveUser(request);
            logger.info("새 사용자 저장: {}", request.getEmail());
        } catch (DupException e) {
            logger.info("사용자가 이미 존재함: {}", request.getEmail());
        }
    }

    private void handleGet(String line) {
        String email = line.substring(4);
        try {
            User user = getUserService.getUser(email);
            logger.info("사용자 정보: {}, {}", user.getEmail(), user.getName());
        } catch (NoUserException e) {
            logger.info("사용자가 존재하지 않음: {}", email);
        }
    }

    private void handleChangeName(String line) {
        String[] v = line.substring(12).split(" ");
        String email = v[0];
        String newName = v[1];
        try {
            changeNameService.changeName(email, newName);
            logger.info("사용자 이름 변경: {}, {}", email, newName);
        } catch (NoUserException e) {
            logger.info("사용자가 존재하지 않음: {}", email);
        }
    }

    private void handleRemove(String line) {
        String email = line.substring(7);
        try {
            removeUserService.removeUser(email);
            logger.info("사용자 삭제함: {}", email);
        } catch (NoUserException e) {
            logger.info("사용자가 존재하지 않음: {}", email);
        }
    }
}
