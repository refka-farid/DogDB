package com.udacity.dogrest.service;

import com.udacity.dogrest.entity.Dog;
import com.udacity.dogrest.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogRepository dogRepository;



    @Override
    public List<Dog> retrieveDogs() {
        return dogRepository.findAll();
    }

    @Override
    public List<String> retrieveDogBreed() {
        return dogRepository.retrieveAllBreed();
    }

    @Override
    public String retrieveDogBreedById(Long id) {
        Optional<String> optionalBreed = Optional.ofNullable(dogRepository.findBreedById(id));
        String breed = optionalBreed.orElseThrow(DogNotFoundException::new);
        return breed;
    }

    @Override
    public List<String> retrieveDogNames() {
        return dogRepository.findAllName();
    }

    public void saveDog(Dog dog){
        dogRepository.save(dog);
        // some other queries and method calls here maybe
        throw new RuntimeException("I failed");
    }

    @Transactional
    public void saveDogTransactional(Dog dog) {
        dogRepository.save(dog);
        // some other queries and method calls here maybe
        throw new RuntimeException("I failed");
    }
}
