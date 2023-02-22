package com.dripchip.demo.controllers;


import com.dripchip.demo.dao.DAO;
import com.dripchip.demo.models.Account;
import com.dripchip.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "api/v1/accounts")
public class AccountController {

    private AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @GetMapping(path = "{accountId}")
    public Optional<Account> getAccount(@PathVariable("accountId") Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        return account;
    }

    @GetMapping(path = "/search")
    public List<Account> searchAccount(String query,
                                         @PathVariable(required = false) String firstName,
                                         @PathVariable(required = false) String lastName,
                                         @PathVariable(required = false) String email,
                                         @PathVariable(required = false) Integer from,
                                         @PathVariable(required = false) Integer size){
//        if (){
//
//        }
        List<Account> accounts = accountRepository.searchAccount(query);
//        if (!accountRepository.existsById(accountId)){
//            throw new IllegalStateException("account with id "+ accountId + " does not exists");
//        }
//        accountRepository.deleteById(accountId);



        return accounts;
    }
    @PostMapping(path = "/registration")
    public Account addNewAccount(@RequestBody Account account){

        accountRepository.save(account);
        return account;
    }
    @DeleteMapping(path = "{accountId}")
    public Optional<Account> deleteAccount(@PathVariable("accountId") Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        if (!accountRepository.existsById(accountId)){
            throw new IllegalStateException("account with id "+ accountId + " does not exists");
        }

        accountRepository.deleteById(accountId);
        return account;
    }

    @PutMapping(path = "{accountId}")
    public void updateAccount(@PathVariable("accountId") Long accountId,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String password){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("account with id" + accountId + "does not exists"));
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPassword(password);

    }
}
