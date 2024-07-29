# job-portal
Job Portal - Recruiters can post Job and Seekers can apply for Job
requires Java 21,mySql database

#Setup the database
mysql -uroot -p<your-root-pwd>
source ~/projects/personal/job-portal/src/main/scripts/create-script-mysql.txt

Error Note
-----
When you login as recruiter and click on Job that has some candidates applied 
and click on link of a candidate listed in "Candidates Applied for Job"
You will get error page. reason being jpa session is not open in view and
you try to lazily load an entity. 
Work-around is to set this in application.yml, but this is not the right way.
In fact true is the default value and we have explicitly turned it off to check
whether we get this isse. It does appear in this scenario

    jpa:
        open-in-view: true
