package com.dripchip.demo.controllers;

import com.dripchip.demo.dao.DAO;
import com.dripchip.demo.models.Account;
import com.dripchip.demo.models.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Pavel
 * @version 1.0
 * @date 12.02.2023 14:06
 */
@RestController
@RequestMapping(path = "api/v1/animals")
public class AnimalController {
    private final DAO<Animal> dao;
    @Autowired
    public AnimalController(DAO<Animal> dao){
        this.dao = dao;
    }


    @GetMapping
    public List<Animal> getAllAnimals(){
        List<Animal> animals = dao.list();
        return animals;
    }

    @GetMapping(path = "{animalId}")
    public Optional<Animal> getAnimal(@PathVariable("animalId") Long animalId){
        Optional<Animal> animal = dao.get(animalId);
        return animal;
    }

    @GetMapping(path = "/search")
    public List<Account> searchAnimal(@RequestParam(value = "firstName", required = false, defaultValue = "null") String firstName,
                                       @RequestParam(value = "lastName", required = false, defaultValue = "null") String lastName,
                                       @RequestParam(value = "email", required = false, defaultValue = "null") String email,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        List<Animal> animals = dao.search(email.toLowerCase(), firstName.toLowerCase(), lastName.toLowerCase(), from, size);
//        if (!accountRepository.existsById(accountId)){
//            throw new IllegalStateException("account with id "+ accountId + " does not exists");
//        }
//        accountRepository.deleteById(accountId);



        return animals;
    }
    @PostMapping(path = "/addAnimal")
    public Animal addNewAnimal(@RequestBody Animal animal){
        dao.create(animal);
        return animal;
    }
    @DeleteMapping(path = "{animalId}")
    public Optional<Animal> deleteAccount(@PathVariable("animalId") Long animalId){
        Optional<Animal> animal = dao.get(animalId);
        if (animal.isEmpty()){
            throw new IllegalStateException("animal with id "+ animalId + " does not exists");
        }

        dao.delete(animalId);
        return animal;
    }

    @PutMapping(path = "{animalId}")
    public Animal updateAnimal(@PathVariable("animalId") Long animalId,
                               @RequestParam(required = false) List<Long> animalTypes,
                               @RequestParam(required = false) Long weight,
                               @RequestParam(required = false) String length,
                               @RequestParam(required = false) String height,
                               @RequestParam(required = false) String gender,
                               @RequestParam(required = false) String lifeStatus,
                               @RequestParam(required = false) LocalDateTime chippingDateTime,
                               @RequestParam(required = false) Integer chipperId,
                               @RequestParam(required = false) Long chippingLocationId,
                               @RequestParam(required = false) List<Long> visitedLocations,
                               @RequestParam(required = false) LocalDateTime deathDateTime){
        Animal animal = dao.get(animalId).orElseThrow(() -> new IllegalStateException("animal with id " + animalId + "does not exists"));
        dao.update(animal, animalId);
        return animal;
    }
}
