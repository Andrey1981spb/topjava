package ru.javawebinar.topjava.jdbc;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.service.UserServiceTest;

@ContextConfiguration ( {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
} )
@RunWith ( SpringRunner.class )
@Sql ( scripts = "classpath:db/populateDB.sql", config = @SqlConfig ( encoding = "UTF-8" ) )
@ActiveProfiles ( "jdbc" )
public class JdbcUserServiceTest extends UserServiceTest {

}
