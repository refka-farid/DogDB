package com.udacity.dogrest;

import com.udacity.dogrest.entity.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DogRestOverviewApplicationTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    void verifyDogCanBeSaved() {
        final Dog dog = new Dog();
        dog.setName("Rex");
        dog.setBreed("Duberman");
        dog.setOrigin("Germany");

        entityManager.persist(dog);

        final List<Dog> dogs = entityManager
                .createQuery("SELECT d FROM Dog d", Dog.class)
                .getResultList();

        assertThat(dogs)
                .hasSize(6)
                .last()
                .isEqualTo(dog);
    }
}
