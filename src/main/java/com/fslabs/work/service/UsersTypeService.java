package com.fslabs.work.service;

import com.fslabs.work.entity.UsersType;
import com.fslabs.work.repository.UsersTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService
{

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository)
    {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll()
    {
        return usersTypeRepository.findAll();
    }
}
