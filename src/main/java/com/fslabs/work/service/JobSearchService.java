package com.fslabs.work.service;

import com.fslabs.work.entity.AppliedJobs;
import com.fslabs.work.entity.BookMarkedJobs;
import com.fslabs.work.entity.JobPostActivity;
import com.fslabs.work.entity.JobSeekerProfile;
import com.fslabs.work.repository.AppliedJobsRespository;
import com.fslabs.work.repository.BookMarkedJobsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JobSearchService
{
    private final AppliedJobsRespository appliedRepository;
    private final BookMarkedJobsRepository bookeMarkedRepository;


    public JobSearchService(AppliedJobsRespository appliedRepository, BookMarkedJobsRepository bookeMarkedRepository)
    {
        this.appliedRepository = appliedRepository;
        this.bookeMarkedRepository = bookeMarkedRepository;
    }

    public List<AppliedJobs> findJobsAppliedByUser(JobSeekerProfile userAccountId)
    {
        return appliedRepository.findByUserId(userAccountId);
    }

    public List<AppliedJobs> findUsersAppliedForJob(JobPostActivity job)
    {
        return appliedRepository.findByJob(job);
    }

    public List<BookMarkedJobs> findJobsBookMarkedByUser(JobSeekerProfile userAccountId)
    {
        return bookeMarkedRepository.findByUserId(userAccountId);
    }

    public List<BookMarkedJobs> findUsersBookMarkedForJob(JobPostActivity job)
    {
        return bookeMarkedRepository.findByJob(job);
    }

    public void addNew(AppliedJobs jobSeekerApply)
    {
        appliedRepository.save(jobSeekerApply);
    }


}
