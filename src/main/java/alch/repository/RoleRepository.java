package alch.repository;

import alch.model.user.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value="roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {
}