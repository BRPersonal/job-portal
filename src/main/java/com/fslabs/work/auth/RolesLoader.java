package com.fslabs.work.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class RolesLoader implements CommandLineRunner
{
    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;

    @Value("${application.security-portal.user}")
    private String securityPortalUserName;

    @Value("${application.security-portal.password}")
    private String securityPortalPassword;


    @Override
    public void run(String... args) throws Exception
    {
        log.debug("Downloading Roles...");
        List<String> roles = List.of("RECRUITER","JOB_SEEKER");
        insertRoles(roles);
    }

    private void insertRoles(List<String> roles)
    {
        String updateSql = "update users_type set user_type_name = ? where user_type_name = ?";
        String insertSql = "insert into users_type(user_type_name) values (?)";

        for (String role : roles)
        {
            log.debug("Checking if role {} exists", role);

            int rowCount = jdbcTemplate.update(updateSql,role,role);
            if (rowCount == 0)
            {
                log.debug("Inserting role {}", role);
                jdbcTemplate.update(insertSql, role);
            }
        }
    }
}
