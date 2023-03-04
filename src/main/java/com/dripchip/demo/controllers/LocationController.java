package com.dripchip.demo.controllers;

import com.dripchip.demo.dao.DAO;
import com.dripchip.demo.models.AnimalType;
import com.dripchip.demo.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel
 * @version 1.0
 * @date 24.02.2023 12:08
 */
@RestController
@RequestMapping(path = "/locations")
public class LocationController {
    private final DAO<Location> dao;
    @Autowired
    public LocationController(DAO<Location> dao){
        this.dao = dao;
    }
    @GetMapping
    public List<Location> getAllLocations(){
        List<Location> locations = dao.list();
        return locations;
    }

    @GetMapping(path = "{locationId}")
    public Location getLocation(@PathVariable("locationId") Long locationId){
        Location location = dao.get(locationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id " + locationId + "does not exists"));;
        return location;
    }

    @PostMapping
    public Location addLocation(@RequestBody Location location){
        dao.create(location);
        return location;
    }

    @DeleteMapping(path = "{locationId}")
    public Location deleteLocation(@PathVariable("locationId") Long locationId){
        Location location = dao.get(locationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id " + locationId + "does not exists"));;
        dao.delete(locationId);
        return location;
    }

    @PutMapping(path = "{locationId}")
    public Location updateLocation(@PathVariable("locationId") Long locationId,
                                       @RequestBody Location location){
        Location l = dao.get(locationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id " + locationId + "does not exists"));;
        location.setId(l.getId());
        if(location.getLatitude().isNaN()){
            location.setLatitude(l.getLatitude());
        }else{
            location.setLatitude(location.getLatitude());
        }
        if(location.getLongitude().isNaN()){
            location.setLongitude(l.getLongitude());
        }else{
            location.setLongitude(location.getLongitude());
        }
        dao.update(location, locationId);
        return location;
    }

    @GetMapping(path = "/search")
    public List<Location> searchAnimalType(@RequestParam(value = "firstName", required = false, defaultValue = "null") String firstName,
                                             @RequestParam(value = "lastName", required = false, defaultValue = "null") String lastName,
                                             @RequestParam(value = "email", required = false, defaultValue = "null") String email,
                                             @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        List<Location> animalTypes = dao.search(email.toLowerCase(), firstName.toLowerCase(), lastName.toLowerCase(), from, size);
//        if (!accountRepository.existsById(accountId)){
//            throw new IllegalStateException("account with id "+ accountId + " does not exists");
//        }
//        accountRepository.deleteById(accountId);



        return null;
    }
}
