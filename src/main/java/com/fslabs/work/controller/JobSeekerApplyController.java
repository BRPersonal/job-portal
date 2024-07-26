package com.fslabs.work.controller;

import com.fslabs.work.entity.*;
import com.fslabs.work.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Controller
public class JobSeekerApplyController
{

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;
    private final JobSearchService jobSearchService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;

    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UsersService usersService, JobSearchService jobSearchService, RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService)
    {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
        this.jobSearchService = jobSearchService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model)
    {
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<AppliedJobs> jobSeekerApplyList = jobSearchService.findUsersAppliedForJob(jobDetails);
        List<BookMarkedJobs> jobSeekerSaveList = jobSearchService.findUsersBookMarkedForJob(jobDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter")))
            {
                //Recruiter
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if (user != null)
                {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }

            }
            else
            {
                //Seeker
                JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
                if (user != null)
                {
                    boolean exists = false;
                    boolean saved = false;
                    for (AppliedJobs jobSeekerApply : jobSeekerApplyList)
                    {
                        if (jobSeekerApply.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            exists = true;
                            break;
                        }
                    }
                    for (BookMarkedJobs jobSeekerSave : jobSeekerSaveList)
                    {
                        if (jobSeekerSave.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);

                }
            }
        }
        AppliedJobs jobSeekerApply = new AppliedJobs();
        model.addAttribute("applyJob", jobSeekerApply);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "job-details";
    }

    @PostMapping("/job-details/apply/{id}")
    public String apply(@PathVariable("id") int id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if (seekerProfile.isPresent() && jobPostActivity != null)
            {
                AppliedJobs jobSeekerApply = new AppliedJobs();
                jobSeekerApply.setUserId(seekerProfile.get());
                jobSeekerApply.setJob(jobPostActivity);
                jobSeekerApply.setApplyDate(new Date());
                jobSearchService.addNew(jobSeekerApply);
            }
            else
            {
                throw new RuntimeException("User not found");
            }


        }

        return "redirect:/dashboard";
    }

}
