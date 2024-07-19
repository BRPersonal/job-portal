package com.fslabs.work.service;

import com.fslabs.work.entity.IRecruiterJobs;
import com.fslabs.work.entity.JobCompany;
import com.fslabs.work.entity.JobLocation;
import com.fslabs.work.model.RecruiterJobsDto;
import com.fslabs.work.entity.JobPostActivity;
import com.fslabs.work.repository.JobPostActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository)
    {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter)
    {

        List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);
        log.debug("Retrieved recruiter Jobs count={}", recruiterJobsDtos.size());

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for (IRecruiterJobs rec : recruiterJobsDtos) {
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
            JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(),
                    rec.getJob_title(), loc, comp));
        }
        return recruiterJobsDtoList;

    }

    public JobPostActivity getOne(int id)
    {
        return jobPostActivityRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Job not found"));
    }
}
