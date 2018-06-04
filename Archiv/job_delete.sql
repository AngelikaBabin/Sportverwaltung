BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"D4A07"."J_CLEAN_OLD_VERIFICATIONS"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'D4A07.P_CLEAN_OLD_VERIFICATIONS',
            number_of_arguments => 0,
            start_date => NULL,
            repeat_interval => 'FREQ=DAILY',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => '');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"D4A07"."J_CLEAN_OLD_VERIFICATIONS"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"D4A07"."J_CLEAN_OLD_VERIFICATIONS"');
END;
