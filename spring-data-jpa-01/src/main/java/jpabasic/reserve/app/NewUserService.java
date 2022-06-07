package jpabasic.reserve.app;

import jpabasic.reserve.domain.User;
import jpabasic.reserve.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NewUserService {

    private UserRepository userRepository;

    public NewUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(SaveRequest saveRequest) {
        Optional<User> userOpt = userRepository.findById(saveRequest.getEmail());
        if (userOpt.isPresent()) throw new DupException();
        User newUser = new User(saveRequest.getEmail(), saveRequest.getName(), LocalDateTime.now());
        userRepository.save(newUser);
    }
}
