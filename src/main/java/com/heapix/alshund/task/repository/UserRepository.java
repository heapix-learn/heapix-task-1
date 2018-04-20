package com.heapix.alshund.task.repository;

import com.heapix.alshund.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT EXISTS(SELECT id FROM users WHERE username = :username)", nativeQuery = true)
    BigInteger existByUsername(@Param("username") String username);

    @Query(value = "SELECT EXISTS(SELECT id FROM users WHERE email = :email)", nativeQuery = true)
    BigInteger existByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);
}
