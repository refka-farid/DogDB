package com.udacity.dogrest;

import com.udacity.dogrest.entity.Dog;
import com.udacity.dogrest.repository.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

@DataJpaTest
 class PagingAndSortingTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        dogRepository.deleteAll();
    }

    @Test
    void shouldSortDogByNameTest() {
        final Dog dog1 = createDog("Fluffy");
        final Dog dog2 = createDog("Spot");
        final Dog dog3 = createDog("Ginger");

        dogRepository.save(dog1);
        dogRepository.save(dog2);
        dogRepository.save(dog3);

        final Iterable<Dog> dogsSortedByName = dogRepository.findAll(Sort.by("name"));
        assertThat(dogsSortedByName).hasSize(3);
        final Iterator<Dog> iterator = dogsSortedByName.iterator();

        assertThat(iterator.next().getName()).isEqualTo("Fluffy");
        assertThat(iterator.next().getName()).isEqualTo("Ginger");
        assertThat(iterator.next().getName()).isEqualTo("Spot");

    }

    @Test
    void shouldSortFlightsByNameAndThenBreedTest() {
        final Dog dog1 = createDog("Fluffy", "Pomeranian");
        final Dog dog2 = createDog("Spot", "Pit Bull");
        final Dog dog3 = createDog("Ginger", "Cocker Spaniel");

        dogRepository.save(dog1);
        dogRepository.save(dog2);
        dogRepository.save(dog3);

        final Iterable<Dog> dogsSortedByName = dogRepository.findAll(Sort.by("name", "breed"));
        assertThat(dogsSortedByName).hasSize(3);
        final Iterator<Dog> iterator = dogsSortedByName.iterator();

        assertThat(iterator.next()).isEqualTo(dog1);
        assertThat(iterator.next()).isEqualTo(dog3);
        assertThat(iterator.next()).isEqualTo(dog2);
    }

    @Test
    void shouldPageResultTest() {

        for (int i = 0; i < 50; i++) {
            dogRepository.save(createDog(String.valueOf(i)));
        }

        final Page<Dog> page = dogRepository.findAll(PageRequest.of(2, 5));

        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent())
                .extracting(Dog::getName)
                .containsExactly("10", "11", "12", "13", "14");
    }

    @Test
    void shouldPageAndSortResultTest() {

        for (int i = 0; i < 50; i++) {
            dogRepository.save(createDog(String.valueOf(i)));
        }

        final Page<Dog> page = dogRepository.findAll(PageRequest.of(2, 5, Sort.by(DESC, "name")));

        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent())
                .extracting(Dog::getName)
                .containsExactly("44", "43", "42", "41", "40");
    }

    @Test
    void shouldPageAndSortADerivedQueryTest() {

        for (int i = 0; i < 10; i++) {
            final Dog dog = createDog(String.valueOf(i));
            dog.setOrigin("Mountain View, CA");
            dogRepository.save(dog);
        }

        for (int i = 0; i < 10; i++) {
            final Dog dog = createDog(String.valueOf(i));
            dog.setOrigin("The North");
            dogRepository.save(dog);
        }

        final Page<Dog> page = dogRepository
                .findByOrigin("Mountain View, CA"
                        , PageRequest.of(0, 5, Sort.by(DESC, "name")));

        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent())
                .extracting(Dog::getName)
                .containsExactly("9", "8", "7", "6", "5");
    }


    private Dog createDog(String name) {
        final Dog dog = new Dog();
        dog.setName(name);
        dog.setBreed("Duberman");
        dog.setOrigin("Germany");
        return dog;
    }

    private Dog createDog(String name, String breed) {
        final Dog dog = new Dog();
        dog.setName(name);
        dog.setBreed(breed);
        dog.setOrigin("Germany");
        return dog;
    }
}
