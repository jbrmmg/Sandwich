package com.jbr.sandwich.dataaccess;

import com.jbr.sandwich.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
