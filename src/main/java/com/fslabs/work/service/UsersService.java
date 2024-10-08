package com.fslabs.work.service;

import com.fslabs.work.entity.JobSeekerProfile;
import com.fslabs.work.entity.RecruiterProfile;
import com.fslabs.work.repository.JobSeekerProfileRepository;
import com.fslabs.work.repository.RecruiterProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fslabs.work.entity.Users;
import com.fslabs.work.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UsersService
{

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository,
                        JobSeekerProfileRepository jobSeekerProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository,
                        PasswordEncoder passwordEncoder)
    {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Users addNew(Users users)
    {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        String userRole = savedUser.getUserTypeId().getUserTypeName();
        log.debug("Saving new user with role={}", userRole);

        if (userRole.equals("RECRUITER"))
        {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        else if (userRole.equals("JOB_SEEKER"))
        {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        else
        {
            throw new RuntimeException(String.format("Unknown Role:%s",userRole));
        }

        return savedUser;
    }

    public Object getCurrentUserProfile()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not find " + "user"));
            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("RECRUITER")))
            {
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;
            }
            else
            {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }

        return null;
    }

    public Users getCurrentUser()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            return user;
        }

        return null;
    }

    public Optional<Users> getUserByEmail(String email)
    {
        return usersRepository.findByEmail(email);
    }

    public Users findByEmail(String currentUsername)
    {
        return usersRepository.findByEmail(currentUsername).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }
}
