package com.dripchip.demo.controllers;


import com.dripchip.demo.dao.AccountJdbcDAO;
import com.dripchip.demo.dao.DAO;
import com.dripchip.demo.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "api/v1/accounts")
public class AccountController {
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
    public Optional<Account> getAccount(@PathVariable("accountId") Long accountId){
        Optional<Account> account = dao.get(accountId);
        return account;
    }

    @PostMapping(path = "/registration")
    public Account registration(@RequestBody Account account){
        dao.create(account);
        return account;
    }

    @DeleteMapping(path = "{accountId}")
    public Optional<Account> deleteAccount(@PathVariable("accountId") Long accountId){
        Optional<Account> account = dao.get(accountId);
        if (account.isEmpty()){
            throw new IllegalStateException("account with id "+ accountId + " does not exists");
        }
        dao.delete(accountId);
        return account;
    }

    @PutMapping(path = "{accountId}")
    public Account updateAccount(@PathVariable("accountId") Long accountId,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String password){
        Account account = dao.get(accountId).orElseThrow(() -> new IllegalStateException("account with id " + accountId + "does not exists"));
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
