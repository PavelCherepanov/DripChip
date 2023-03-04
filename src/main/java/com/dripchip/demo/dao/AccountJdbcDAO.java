package com.dripchip.demo.dao;

import com.dripchip.demo.models.Account;
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
 * @date 22.02.2023 18:53
 */
@Component
public class AccountJdbcDAO implements DAO<Account>{

    private static final Logger log = LoggerFactory.getLogger(AccountJdbcDAO.class);
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public AccountJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper rowMapper = (rs, rowNum)->{
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setEmail(rs.getString("email"));
        account.setFirstName(rs.getString("first_name"));
        account.setLastName(rs.getString("last_name"));
        account.setPassword(rs.getString("password"));
        return account;
    };

    RowMapper rowMapperForSearch = (rs, rowNum)->{
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setEmail(rs.getString("email"));
        account.setFirstName(rs.getString("first_name"));
        account.setLastName(rs.getString("last_name"));
        return account;
    };

    @Override
    public List<Account> list() {
        String sql = "SELECT id, email, first_name, last_name, password FROM accounts";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Account account) {
        String sql = "INSERT INTO accounts(id, email, first_name, last_name, password) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql, account.getId(), account.getEmail(), account.getFirstName(), account.getLastName(), account.getPassword());
    }

    @Override
    public Optional<Account> get(Long id) {
        String sql = "SELECT id, email, first_name, last_name, password FROM accounts WHERE id = ?";
        Account account = null;
        try{
            account = (Account) jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException exception){
            log.info("Account not found: " + id);
        }

        return Optional.ofNullable(account);
    }

    @Override
    public void update(Account account, Long id) {
        String sql = "UPDATE accounts SET email=?, first_name=?, last_name=?, password=? WHERE id = ?";
        jdbcTemplate.update(sql, account.getEmail(), account.getFirstName(), account.getLastName(), account.getPassword(), account.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Account getAccountByEmail(String email){
        String sql = "SELECT id, email, first_name, last_name, password FROM accounts WHERE email=?";
        Account account = (Account) jdbcTemplate.query(sql, new Object[]{email}, rowMapper);
        return account;
    }

    @Override
    public List<Account> search(String email, String firstName, String lastName, Integer from, Integer size) {
        String sql = "SELECT a.id, a.email, a.first_name, a.last_name FROM accounts a WHERE LOWER(a.email) LIKE CONCAT('%',?,'%') OR " +
                "LOWER(a.first_name) LIKE CONCAT('%',?,'%') OR " +
                "LOWER(a.last_name) LIKE CONCAT('%',?,'%') AND a.id = ? LIMIT ?";
        List<Account> accounts = jdbcTemplate.query(sql, new Object[]{email, firstName, lastName, from, size}, rowMapperForSearch);
        return accounts;
    }


}
