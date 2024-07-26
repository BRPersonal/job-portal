package com.fslabs.work.repository;

import org.springframework.stereotype.Repository;
import com.fslabs.work.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer>
{
}
