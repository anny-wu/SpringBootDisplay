package com.annyw.springboot.listener;

import com.annyw.springboot.bean.Privilege;
import com.annyw.springboot.bean.Role;
import com.annyw.springboot.repo.PrivilegeRepository;
import com.annyw.springboot.repo.RoleRepository;
import com.annyw.springboot.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class DataLoader implements
    ApplicationListener<ContextRefreshedEvent> {
    
    boolean setup = false;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        if (setup){return;}
        Privilege addPrivilege = createPrivilegeIfNotFound("ADD_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        Privilege editPrivilege = createPrivilegeIfNotFound("EDIT_PRIVILEGE");
        List<Privilege> privilegesU = new ArrayList<>();
        privilegesU.add(editPrivilege);
        createRoleIfNotFound("ROLE_USER", privilegesU);
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(editPrivilege);
        privileges.add(addPrivilege);
        privileges.add(deletePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", privileges);
        
        setup = true;
    }
    
    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
    
    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
