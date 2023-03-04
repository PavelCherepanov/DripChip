package com.dripchip.demo.controllers;

import com.dripchip.demo.dao.AccountJdbcDAO;
import com.dripchip.demo.dao.DAO;
import com.dripchip.demo.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final DAO<Account> dao;
    @Autowired
    public AccountController(DAO<Account> dao){
        this.dao = dao;
    }
    @GetMapping
    public List<Account> getAllAccounts(){
        List<Account> accounts = dao.list();
        return accounts;
    }

    @GetMapping(path = "{accountId}")
    public Account getAccount(@PathVariable("accountId") Long accountId){
        Account account = dao.get(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with id " + accountId + " does not exists"));;;;
        return account;
    }

    @PostMapping(path = "/registration")
    public Account registration(@RequestBody Account account){
        if (account.getFirstName().isBlank() ||
                account.getLastName().isBlank() ||
                account.getEmail().isBlank() ||
                account.getPassword().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");

        }
        AccountJdbcDAO accountJdbcDAO = new AccountJdbcDAO(jdbcTemplate);

        if (accountJdbcDAO.getAccountByEmail(account.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflict");
        }
        dao.create(account);
        return account;
    }

    @DeleteMapping(path = "{accountId}")
    public Account deleteAccount(@PathVariable("accountId") Long accountId){
        Account account = dao.get(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with id " + accountId + " does not exists"));;;;
        dao.delete(accountId);
        return account;
    }

    @PutMapping(path = "{accountId}")
    public Account updateAccount(@PathVariable("accountId") Long accountId,
                                 @RequestBody Account account){
        Account a = dao.get(accountId).orElseThrow(() -> new IllegalStateException("account with id " + accountId + "does not exists"));
        if(account.getPassword().isEmpty()){
            account.setPassword(a.getPassword());
        }else{
            account.setPassword(account.getPassword());
        }
        if(account.getEmail().isEmpty()){
            account.setEmail(a.getEmail());
        }else{
            account.setEmail(account.getEmail());
        }
        if(account.getFirstName().isEmpty()){
            account.setFirstName(a.getFirstName());
        }else{
            account.setFirstName(account.getFirstName());
        }
        if(account.getLastName().isEmpty()){
            account.setLastName(a.getLastName());
        }else{
            account.setLastName(account.getLastName());
        }
        dao.update(account, accountId);
        return account;
    }

    @GetMapping(path = "/search")
    public List<Account> searchAccount(@RequestParam(value = "firstName", required = false, defaultValue = "null") String firstName,
                                       @RequestParam(value = "lastName", required = false, defaultValue = "null") String lastName,
                                       @RequestParam(value = "email", required = false, defaultValue = "null") String email,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        List<Account> accounts = dao.search(email.toLowerCase(), firstName.toLowerCase(), lastName.toLowerCase(), from, size);
//        if (!accountRepository.existsById(accountId)){
//            throw new IllegalStateException("account with id "+ accountId + " does not exists");
//        }
//        accountRepository.deleteById(accountId);



        return accounts;
    }
}
