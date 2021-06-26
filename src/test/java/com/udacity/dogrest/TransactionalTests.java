package com.udacity.dogrest;

import com.udacity.dogrest.entity.Dog;
import com.udacity.dogrest.repository.DogRepository;
import com.udacity.dogrest.service.DogServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTests {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogServiceImpl dogService;

    @Before
    public void setUp() {
        dogRepository.deleteAll();
    }

    @Test
    public void shouldNotRollBackWhenTheresNoTransaction() {
        try {
            dogService.saveDog(new Dog());
        } catch (Exception e) {
            // Do nothing
        } finally {
            assertThat(dogRepository.findAll()).isNotEmpty();
        }
    }

    @Test
    public void shouldNotRollBackWhenTheresIsATransaction() {
        try {
            dogService.saveDogTransactional(new Dog());
        } catch (Exception e) {
            // Do nothing
        } finally {
            assertThat(dogRepository.findAll())
                    .isEmpty();
        }
    }

}
