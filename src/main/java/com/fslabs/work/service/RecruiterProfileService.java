package com.fslabs.work.service;

    import com.fslabs.work.entity.RecruiterProfile;
import com.fslabs.work.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository)
    {
        this.recruiterRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer id)
    {
        return recruiterRepository.findById(id);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile)
    {
        return recruiterRepository.save(recruiterProfile);
    }

}
