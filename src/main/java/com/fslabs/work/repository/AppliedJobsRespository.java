package com.fslabs.work.repository;

import com.fslabs.work.entity.AppliedJobs;
import com.fslabs.work.entity.JobPostActivity;
import com.fslabs.work.entity.JobSeekerProfile;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface AppliedJobsRespository extends JpaRepository<AppliedJobs,Integer>
{
    List<AppliedJobs> findByUserId(JobSeekerProfile userAccountId);
    List<AppliedJobs> findByJob(JobPostActivity job);
}
