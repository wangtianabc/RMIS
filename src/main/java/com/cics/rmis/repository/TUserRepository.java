package com.cics.rmis.repository;

import com.cics.rmis.model.TUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TUserRepository extends JpaRepository<TUser, Integer> {
    TUser findByName(String name);
    TUser findByNameAndAge(String name, Integer age);
    @Query("from TUser u where u.name=:name")
    TUser findUser(@Param("name") String name);
    Page<TUser> findAll(Pageable pageable);
    TUser findByUsername(String username);
}
