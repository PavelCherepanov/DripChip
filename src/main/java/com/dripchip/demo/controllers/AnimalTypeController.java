package com.dripchip.demo.controllers;

import com.dripchip.demo.dao.DAO;
import com.dripchip.demo.models.Account;
import com.dripchip.demo.models.Animal;
import com.dripchip.demo.models.AnimalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel
 * @version 1.0
 * @date 24.02.2023 10:50
 */
@RestController
@RequestMapping(path = "/animals/types")
public class AnimalTypeController {
    private final DAO<AnimalType> dao;
    @Autowired
    public AnimalTypeController(DAO<AnimalType> dao){
        this.dao = dao;
    }
    @GetMapping
    public List<AnimalType> getAllAnimalType(){
        List<AnimalType> animalTypes = dao.list();
        return animalTypes;
    }

    @GetMapping(path = "{animalTypeId}")
    public AnimalType getAnimalType(@PathVariable("animalTypeId") Long animalTypeId){
        AnimalType animalType = dao.get(animalTypeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AnimalType with id " + animalTypeId + " does not exists"));;;;
        return animalType;
    }

    @PostMapping
    public AnimalType addAnimalType(@RequestBody AnimalType animalType){
        dao.create(animalType);
        return animalType;
    }

    @DeleteMapping(path = "{animalTypeId}")
    public AnimalType deleteAnimalType(@PathVariable("animalTypeId") Long animalTypeId){
        AnimalType animalType = dao.get(animalTypeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AnimalType with id " + animalTypeId + " does not exists"));;;
        dao.delete(animalTypeId);
        return animalType;
    }

    @PutMapping(path = "{animalTypeId}")
    public AnimalType updateAnimalType(@PathVariable("animalTypeId") Long animalTypeId,
                                 @RequestBody AnimalType animalType){
        AnimalType type = dao.get(animalTypeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AnimalType with id " + animalTypeId + " does not exists"));;
        animalType.setId(type.getId());
        if(animalType.getType().isEmpty()){
            animalType.setType(type.getType());
        }else{
            animalType.setType(animalType.getType());
        }
        dao.update(animalType, animalTypeId);
        return animalType;
    }

    @GetMapping(path = "/search")
    public List<AnimalType> searchAnimalType(@RequestParam(value = "firstName", required = false, defaultValue = "null") String firstName,
                                       @RequestParam(value = "lastName", required = false, defaultValue = "null") String lastName,
                                       @RequestParam(value = "email", required = false, defaultValue = "null") String email,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        List<AnimalType> animalTypes = dao.search(email.toLowerCase(), firstName.toLowerCase(), lastName.toLowerCase(), from, size);
//        if (!accountRepository.existsById(accountId)){
//            throw new IllegalStateException("account with id "+ accountId + " does not exists");
//        }
//        accountRepository.deleteById(accountId);



        return null;
    }
}
