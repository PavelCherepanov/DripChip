package com.dripchip.demo.dao;

import com.dripchip.demo.models.AnimalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel
 * @version 1.0
 * @date 24.02.2023 10:23
 */
@Component
public class AnimalTypeJdbcDAO implements DAO<AnimalType>{
    private static final Logger log = LoggerFactory.getLogger(AccountJdbcDAO.class);
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public AnimalTypeJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper rowMapper = (rs, rowNum)->{
        AnimalType animalType = new AnimalType();
        animalType.setId(rs.getLong("id"));
        animalType.setType(rs.getString("type"));
        return animalType;
    };

    @Override
    public List list() {
        String sql = "SELECT  id, type FROM animal_types";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(AnimalType animalType) {
        String sql = "INSERT INTO animal_types(id, type) VALUES(?,?)";
        jdbcTemplate.update(sql, animalType.getId(), animalType.getType());
    }

    @Override
    public Optional<AnimalType> get(Long id) {
        String sql = "SELECT  id, type FROM animal_types WHERE id = ?";
        AnimalType animalType = null;
        try{
            animalType = (AnimalType) jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException exception){
            log.info("AnimalType not found: " + id);
        }

        return Optional.ofNullable(animalType);
    }

    @Override
    public void update(AnimalType animalType, Long id) {
        String sql = "UPDATE animal_types SET type=? WHERE id = ?";
        jdbcTemplate.update(sql, animalType.getType(), animalType.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM animal_types WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<AnimalType> search(String email, String firstName, String lastName, Integer from, Integer size) {
        return null;
    }
}
