package com.jbr.sandwich.dataaccess;

import com.jbr.sandwich.data.UserDayId;
import com.jbr.sandwich.data.UserDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDayRepository extends JpaRepository<UserDay, UserDayId> {
}
