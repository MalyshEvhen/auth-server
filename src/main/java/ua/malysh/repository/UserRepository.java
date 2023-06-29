package ua.malysh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.malysh.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.username = ?1")
    boolean existsByUsername(String username);

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}
