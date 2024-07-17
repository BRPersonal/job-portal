package com.fslabs.work.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.fslabs.work.entity.Users;
import com.fslabs.work.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService
{

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository,PasswordEncoder passwordEncoder)
    {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users)
    {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);

        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email)
    {
        return usersRepository.findByEmail(email);
    }
}
