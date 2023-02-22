package com.dripchip.demo.dao;

import com.dripchip.demo.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Override
    public List<Account> list() {
        String sql = "SELECT  id, email, first_name, last_name, password FROM accounts";
        return jdbcTemplate.query(sql, (rs, rowNum)->{
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setEmail(rs.getString("email"));
            account.setFirstName(rs.getString("first_name"));
            account.setLastName(rs.getString("last_name"));
            account.setPassword(rs.getString("password"));
            return account;
        });
    }

    @Override
    public void create(Account account) {

    }

    @Override
    public Optional<Account> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Account account, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
