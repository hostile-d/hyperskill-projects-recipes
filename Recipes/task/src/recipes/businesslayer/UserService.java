package recipes.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import recipes.persistence.UserRepository;
import java.util.*;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<User> users = userRepository.findByEmail(username);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + username);
        }

        return new UserDetailsImpl(users.iterator().next());
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void save (User user) {
        userRepository.save(new User(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRecipes()
        ));
    }
}