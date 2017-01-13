package org.pdxi.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pdxi.TestConfig;
import org.pdxi.dao.TumorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jmason on 09/01/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestPropertySource(locations = {"classpath:ogm.properties"})
@Transactional
@SpringBootTest
public class TumorTypeRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(TumorTypeRepositoryTest.class);

    @Autowired
    TumorTypeRepository tumorTypeRepository;

    @Rollback(false)
    @BeforeTransaction
    public void cleanDb() {
        tumorTypeRepository.deleteAll();
    }

    @Test
    public void createTumorTypesInGraphDb() throws Exception {

        List<String> types = Arrays.asList("Metastasis", "Metastatic", "Not Specified", "Primary Malignancy", "Recurrent/Relapse");

        for (String type : types) {
            TumorType foundType = tumorTypeRepository.findByName(type);
            if (foundType == null) {
                log.info("Tumor type ", type, "not found. Creating");
                foundType = new TumorType(type);
                tumorTypeRepository.save(foundType);
            }

            foundType = tumorTypeRepository.findByName(type);
            log.info("Found Tumor type ", type);

            assert (foundType != null);
            assert (foundType.getName().equals(type));

        }

    }

}