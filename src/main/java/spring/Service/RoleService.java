package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Role;
import spring.Repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}
