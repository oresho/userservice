package com.oreoluwa.userService.service;

import com.oreoluwa.userService.domain.AppUser;
import com.oreoluwa.userService.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getAppUser(String userName);
    List<AppUser> getAppUsers();
}
