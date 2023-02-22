package com.dripchip.demo.repositories;

import com.dripchip.demo.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavel
 * @version 1.0
 * @date 12.02.2023 14:07
 */
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
