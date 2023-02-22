package com.dripchip.demo.controllers;

import com.dripchip.demo.models.Account;
import com.dripchip.demo.models.Animal;
import com.dripchip.demo.models.AnimalType;
import com.dripchip.demo.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public List<Animal> getAllAnimals(){
        List<Animal> animals = animalRepository.findAll();
        return animals;
    }

    @GetMapping(path = "{animalId}")
    public Optional<Animal> getAnimal(@PathVariable("animalId") Long animalId){
        Optional<Animal> animal = animalRepository.findById(animalId);
        return animal;
    }

    @GetMapping(path = "search/{accountId}")
    public Optional<Animal> searchAnimal(@PathVariable("animalId") Long accountId){
        Optional<Animal> animal = animalRepository.findById(accountId);
        if (!animalRepository.existsById(accountId)){
            throw new IllegalStateException("animal with id "+ accountId + " does not exists");
        }
        animalRepository.deleteById(accountId);
        return animal;
    }
    @PostMapping(path = "/registration")
    public Animal addNewAnimal(@RequestBody Animal animal){

        animalRepository.save(animal);
        return animal;
    }
    @DeleteMapping(path = "{animalId}")
    public Optional<Animal> deleteAccount(@PathVariable("accountId") Long animalId){
        Optional<Animal> animal = animalRepository.findById(animalId);
        if (!animalRepository.existsById(animalId)){
            throw new IllegalStateException("account with id "+ animalId + " does not exists");
        }

        animalRepository.deleteById(animalId);
        return animal;
    }

//    @PutMapping(path = "{animalId}")
//    public void updateAccount(@PathVariable("animalId") Long animalId,
//                              @RequestParam(required = false) String firstName,
//                              @RequestParam(required = false) String lastName,
//                              @RequestParam(required = false) String email,
//                              @RequestParam(required = false) String password){
//        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new IllegalStateException("account with id" + animalId + "does not exists"));
//        animal.setFirstName(firstName);
//        animal.setLastName(lastName);
//        animal.setEmail(email);
//        animal.setPassword(password);
//
//    }
}
