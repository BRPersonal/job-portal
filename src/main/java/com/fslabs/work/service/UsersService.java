package com.fslabs.work.service;

import com.fslabs.work.entity.Users;
import com.fslabs.work.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService
{

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    public Users addNew(Users users)
    {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        return usersRepository.save(users);
    }

    public Optional<Users> getUserByEmail(String email)
    {
        return usersRepository.findByEmail(email);
    }
}
