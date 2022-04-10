package jpabasic.reserve.app;

import jakarta.persistence.EntityManager;
import jpabasic.reserve.domain.User;
import jpabasic.reserve.jpa.EMF;

public class GetUserService {
    public User getUser(String email) {
        EntityManager em = EMF.createEntityManager();
        try {
            User user = em.find(User.class, email);
            if (user == null) {
                throw new NoUserException();
            }
            return user;
        } finally {
            em.close();
        }
    }
}
