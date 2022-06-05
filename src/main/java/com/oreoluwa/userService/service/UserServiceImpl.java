package com.oreoluwa.userService.service;

import com.oreoluwa.userService.domain.AppUser;
import com.oreoluwa.userService.domain.Role;
import com.oreoluwa.userService.repo.RoleRepo;
import com.oreoluwa.userService.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser =  userRepo.findByUserName(userName);
        if(appUser == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else {
            log.info("User found in the database: {}", userName);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(appUser.getUserName(),appUser.getPassword(),authorities) ;
    }
    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("saving new user {} to the database",appUser.getName());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepo.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {}",roleName,userName);
        AppUser appUser = userRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getAppUser(String userName) {
        log.info("Fetching user {}",userName);
        return userRepo.findByUserName(userName);
    }

    @Override
    public List<AppUser> getAppUsers() {
        log.info("Fetching all Users");
        return userRepo.findAll();
    }

}
