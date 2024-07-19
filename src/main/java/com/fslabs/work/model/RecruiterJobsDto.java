package com.fslabs.work.model;

import com.fslabs.work.entity.JobCompany;
import com.fslabs.work.entity.JobLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RecruiterJobsDto
{
    private Long totalCandidates;
    private Integer jobPostId;
    private String jobTitle;
    private JobLocation jobLocationId;
    private JobCompany jobCompanyId;
}
