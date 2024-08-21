package com.bank.bank_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bank.bank_api.domain.User;

public interface UserRepository extends CrudRepository<User, String> {

    public User findByEmailIgnoreCase(@Param("email") String email);
    // public User findByUsername(String username);

}
