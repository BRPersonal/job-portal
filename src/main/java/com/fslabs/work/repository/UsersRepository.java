package com.fslabs.work.repository;

import com.fslabs.work.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer>
{
    Optional<Users> findByEmail(String email);
}
