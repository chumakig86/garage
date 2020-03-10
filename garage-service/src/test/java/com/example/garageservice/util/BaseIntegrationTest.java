package com.example.garageservice.util;

import com.example.garageservice.model.Garage;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper mapper;

    private static MongodExecutable mongodExecutable;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @After
    public void after() {
        mongoTemplate.dropCollection(Garage.class);
    }


    @BeforeClass
    public static void beforeClass() throws IOException {

        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongoConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(27017, false)).build();

        mongodExecutable = starter.prepare(mongoConfig);

        try {
            mongodExecutable.start();
        } catch (Exception e) {
            closeMongoExecutable();
        }
    }

    @AfterClass
    public static void afterClass() {
        closeMongoExecutable();
    }

    private static void closeMongoExecutable() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
