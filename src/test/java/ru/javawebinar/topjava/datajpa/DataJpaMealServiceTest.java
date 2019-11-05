package ru.javawebinar.topjava.datajpa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.service.MealServiceTest;

@ContextConfiguration ( {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
} )
@RunWith ( SpringRunner.class )
@Sql ( scripts = "classpath:db/populateDB.sql", config = @SqlConfig ( encoding = "UTF-8" ) )
@ActiveProfiles ( "datajpa" )
public class DataJpaMealServiceTest extends MealServiceTest {

}
