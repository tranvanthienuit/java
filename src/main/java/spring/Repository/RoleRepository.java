package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Model.Role;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Transactional
    @Modifying
    @Query("delete from Role u where u.roleId=:roleId ")
    void removeByRoleId(@Param("roleId") String roleId);

    @Query("select u from Role u where u.nameRole=:nameRole")
    Role findRoleByName(@Param("nameRole") String nameRole);
}
