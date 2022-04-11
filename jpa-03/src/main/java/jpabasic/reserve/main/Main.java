package jpabasic.reserve.main;

import jakarta.persistence.EntityExistsException;
import jpabasic.reserve.app.*;
import jpabasic.reserve.domain.User;
import jpabasic.reserve.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static NewUserService newUserService = new NewUserService();
    private static GetUserService getUserService = new GetUserService();
    private static ChangeNameService changeNameService = new ChangeNameService();
    private static RemoveUserService removeUserService = new RemoveUserService();

    public static void main(String[] args) throws IOException {
        EMF.init();
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
        } finally {
            EMF.close();
        }
    }

    private static void handleNew(String line) {
        String[] v = line.substring(4).split(" ");
        User u = new User(v[0], v[1], LocalDateTime.now());
        try {
            newUserService.saveNewUser(u);
            logger.info("새 사용자 저장: {}", u);
        } catch (EntityExistsException e) {
            logger.info("사용자가 이미 존재함: {}", u.getEmail());
        }
    }

    private static void handleGet(String line) {
        String email = line.substring(4);
        try {
            User user = getUserService.getUser(email);
            logger.info("사용자 정보: {}", user);
        } catch (NoUserException e) {
            logger.info("사용자가 존재하지 않음: {}", email);
        }
    }

    private static void handleChangeName(String line) {
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

    private static void handleRemove(String line) {
        String email = line.substring(7);
        try {
            removeUserService.removeUser(email);
            logger.info("사용자 삭제함: {}", email);
        } catch (NoUserException e) {
            logger.info("사용자가 존재하지 않음: {}", email);
        }
    }
}
