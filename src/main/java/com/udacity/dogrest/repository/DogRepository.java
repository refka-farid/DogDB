package com.udacity.dogrest.repository;

import com.udacity.dogrest.entity.Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DogRepository extends PagingAndSortingRepository<Dog, Long> {

//    public static final String FIND_PROJECTS = "SELECT projectId, projectName FROM projects";
//
//    @Query(value = FIND_PROJECTS, nativeQuery = true)
//    public List<Object[]> findProjects();

    List<Dog> findAll();

    @Query("select d.id, d.breed from Dog d where d.id=:id")
    String findBreedById(Long id);

    @Query("select d.id, d.breed from Dog d")
    List<String> retrieveAllBreed();

    @Query("select d.id, d.name from Dog d")
    List<String> findAllName();

    List<Dog> findByOrigin(String origin);

    Page<Dog> findByOrigin(String origin, Pageable pageable);

    List<Dog> findByOriginAndBreed(String origin, String breed);


}