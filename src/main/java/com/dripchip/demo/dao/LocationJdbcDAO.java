package com.dripchip.demo.dao;

import com.dripchip.demo.models.AnimalType;
import com.dripchip.demo.models.Location;
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
 * @date 24.02.2023 12:00
 */
@Component
public class LocationJdbcDAO implements DAO<Location>{
    private static final Logger log = LoggerFactory.getLogger(AccountJdbcDAO.class);
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LocationJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper rowMapper = (rs, rowNum)->{
        Location location = new Location();
        location.setId(rs.getLong("id"));
        location.setLatitude(rs.getDouble("latitude"));
        location.setLongitude(rs.getDouble("longitude"));
        return location;
    };

    @Override
    public List list() {
        String sql = "SELECT id, latitude, longitude FROM locations";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Location location) {
        String sql = "INSERT INTO locations(id, latitude, longitude) VALUES(?,?,?)";
        jdbcTemplate.update(sql, location.getId(), location.getLatitude(), location.getLongitude());
    }

    @Override
    public Optional<Location> get(Long id) {
        String sql = "SELECT id, latitude, longitude FROM locations WHERE id = ?";
        Location location = null;
        try{
            location = (Location) jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException exception){
            log.info("Location not found: " + id);
        }

        return Optional.ofNullable(location);
    }

    @Override
    public void update(Location location, Long id) {
        String sql = "UPDATE locations SET latitude=?, longitude=? WHERE id = ?";
        jdbcTemplate.update(sql, location.getLatitude(), location.getLongitude(), location.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Location> search(String email, String firstName, String lastName, Integer from, Integer size) {
        return null;
    }
}
