package com.fslabs.work.repository;

import com.fslabs.work.entity.BookMarkedJobs;
import com.fslabs.work.entity.JobPostActivity;
import com.fslabs.work.entity.JobSeekerProfile;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface BookMarkedJobsRepository extends JpaRepository<BookMarkedJobs,Integer>
{
    List<BookMarkedJobs> findByUserId(JobSeekerProfile userAccountId);
    List<BookMarkedJobs> findByJob(JobPostActivity job);
}
