package hr.techtitans.users.repositories;


import hr.techtitans.users.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    void deleteById(ObjectId id);
}
