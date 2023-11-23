package hr.techtitans.users.repositories;

import hr.techtitans.users.models.UserRole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends MongoRepository<UserRole, ObjectId> {
    UserRole findByName(String name);
}
