package com.oreoluwa.userService.repo;

import com.oreoluwa.userService.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String userName);
}
