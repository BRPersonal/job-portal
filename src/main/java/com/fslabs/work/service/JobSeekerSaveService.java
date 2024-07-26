package com.fslabs.work.service;

import com.fslabs.work.entity.JobPostActivity;
import com.fslabs.work.entity.JobSeekerProfile;
import com.fslabs.work.entity.BookMarkedJobs;
import com.fslabs.work.repository.BookMarkedJobsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService
{

    private final BookMarkedJobsRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(BookMarkedJobsRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<BookMarkedJobs> getCandidatesJob(JobSeekerProfile userAccountId)
    {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<BookMarkedJobs> getJobCandidates(JobPostActivity job)
    {
        return jobSeekerSaveRepository.findByJob(job);
    }

    public void addNew(BookMarkedJobs jobSeekerSave)
    {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}
