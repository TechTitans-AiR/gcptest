package hr.techtitans.users.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    private ObjectId id;
    private String name;
    private String code;
}
