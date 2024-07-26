package com.fslabs.work.controller;

import com.fslabs.work.entity.JobPostActivity;
import com.fslabs.work.entity.RecruiterProfile;
import com.fslabs.work.entity.Users;
import com.fslabs.work.model.RecruiterJobsDto;
import com.fslabs.work.service.JobPostActivityService;
import com.fslabs.work.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class JobPostActivityController
{
    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    public JobPostActivityController(UsersService usersService,
                                     JobPostActivityService jobPostActivityService)
    {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/add")
    public String showAddJobForm(Model model)
    {
        log.debug("Rendering AddJob Form");

        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String postJob(JobPostActivity jobPostActivity, Model model)
    {

        Users user = usersService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model)
    {

        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }
}
