package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Role;
import spring.Repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public void removeByRoleId(String roleId) {
        roleRepository.removeByRoleId(roleId);
    }

    public Role fineRoleByName(String name) {
        return roleRepository.fineRoleByName(name);
    }

}
