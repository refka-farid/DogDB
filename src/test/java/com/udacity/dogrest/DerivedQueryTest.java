package com.udacity.dogrest;

import com.udacity.dogrest.entity.Dog;
import com.udacity.dogrest.repository.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DerivedQueryTest {

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        dogRepository.deleteAll();
    }

    @Test
    void shouldFindDogsFromGermanyTest() {
        var dog1 = createDog("Germany");
        var dog2 = createDog("Germany");
        var dog3 = createDog("Portugal");

        dogRepository.save(dog1);
        dogRepository.save(dog2);
        dogRepository.save(dog3);

        List<Dog> dogsFromGermany = dogRepository.findByOrigin("Germany");
        assertThat(dogsFromGermany).hasSize(2);
        assertThat(dogsFromGermany.get(0)).isEqualTo(dog1);
        assertThat(dogsFromGermany.get(1)).isEqualTo(dog2);

    }

    @Test
    void shouldFindDogsFromBuffaloAndHuskyTest(){
        var dog1 = createDog("Buffalo, NY");
        var dog2 = createDog("Germany");
        var dog3 = createDog("Buffalo, NY");

        dogRepository.save(dog1);
        dogRepository.save(dog2);
        dogRepository.save(dog3);

        List<Dog> dogsFromGermany = dogRepository.findByOriginAndBreed("Buffalo, NY","Duberman");
        assertThat(dogsFromGermany).hasSize(2);
        assertThat(dogsFromGermany.get(0)).isEqualTo(dog1);
        assertThat(dogsFromGermany.get(1)).isEqualTo(dog3);    }

    private Dog createDog(String origin) {
        final Dog dog = new Dog();
        dog.setName("Rex");
        dog.setBreed("Duberman");
        dog.setOrigin(origin);
        return dog;
    }
}
