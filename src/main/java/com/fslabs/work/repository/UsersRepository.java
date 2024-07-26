package com.fslabs.work.repository;

import com.fslabs.work.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>
{
    Optional<Users> findByEmail(String email);
}
