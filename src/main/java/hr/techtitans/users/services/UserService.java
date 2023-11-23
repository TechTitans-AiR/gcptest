package hr.techtitans.users.services;


import hr.techtitans.users.dtos.UserDto;
import hr.techtitans.users.models.User;
import hr.techtitans.users.models.UserRole;
import hr.techtitans.users.models.UserStatus;
import hr.techtitans.users.repositories.UserRepository;
import hr.techtitans.users.repositories.UserRoleRepository;
import hr.techtitans.users.repositories.UserStatusRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public class UserCreationException extends RuntimeException {
        public UserCreationException(String message) {
            super(message);
        }
    }


    public List<UserDto> allUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserRole userRole = userRoleRepository.findById(user.getUserRole()).orElse(null);
        UserStatus userStatus = userStatusRepository.findById(user.getUserStatus()).orElse(null);
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getDate_of_birth(),
                user.getDate_created(),
                user.getDate_modified(),
                userRole,
                userStatus
        );
    }

    public ResponseEntity<Object> addUser(Map<String,Object> payload) {
        try {
            System.out.println("Sadržaj payload varijable:");
            payload.forEach((key, value) -> System.out.println(key + ": " + value));
            User user = new User();
            LocalDateTime currentDateTime = LocalDateTime.now();

            String[] fieldsToCheck = {"username", "first_name", "last_name","email","password"};
            for (String field : fieldsToCheck) {
                if (!isValidField(payload, field)) {
                    throw new UserCreationException(field + " not valid");
                }
            }

            user.setUsername((String) payload.get("username"));
            user.setFirst_name((String) payload.get("first_name"));
            user.setLast_name((String) payload.get("last_name"));
            user.setEmail((String) payload.get("email"));
            user.setDate_created(currentDateTime);
            user.setDate_modified(currentDateTime);
            user.setAddress((String) payload.get("address"));
            user.setPhone((String) payload.get("phone"));
            user.setPassword((String) payload.get("password"));
            String dateOfBirthString = (String) payload.get("date_of_birth");
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString);
            user.setDate_of_birth(dateOfBirth);
            String userRoleName = (String) payload.get("user_role");
            UserRole userRole = userRoleRepository.findByName(userRoleName);
            System.out.println("userRole");
            System.out.println(userRole.getId());
            if (userRole != null) {
                user.setUserRole(userRole.getId());
            } else {
                user.setUserRole(null);
            }

            String userStatusName = (String) payload.get("user_status");
            UserStatus userStatus = userStatusRepository.findByName(userStatusName);
            System.out.println("userStatus");
            System.out.println(userStatus.getId());
            if (userStatus != null) {
                user.setUserStatus(userStatus.getId());
            } else {
                user.setUserStatus(null);
            }
            userRepository.insert(user);

            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (UserCreationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidField(Map<String, Object> payload, String field) {
        return payload.containsKey(field) && payload.get(field) != null && !payload.get(field).toString().isEmpty();
    }
}
