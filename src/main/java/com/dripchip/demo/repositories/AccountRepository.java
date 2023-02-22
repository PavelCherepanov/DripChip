package com.dripchip.demo.repositories;

import com.dripchip.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a.email, a,firstName, a.lastName FROM accounts a WHERE a.email LIKE CONCAT('%',:query,'%') OR " +
            "a.first_name LIKE CONCAT ('%',:query,'%') OR " +
            "a.last_name LIKE CONCAT ('%',:query,'%')")
    List<Account> searchAccount(String query);
    Account findByEmail(String email);
    Account findByFirstName(String firstName);
}
