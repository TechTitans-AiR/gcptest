package hr.techtitans.users.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    @Id
    private String  id;
    private String name;
}
