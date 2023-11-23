package hr.techtitans.users.controllers;

import hr.techtitans.users.dtos.UserDto;
import hr.techtitans.users.models.User;
import hr.techtitans.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<List<UserDto>>(userService.allUsers(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> addUser(@RequestBody Map<String, Object> payload) {
        try {
            return new ResponseEntity<>(userService.addUser(payload), HttpStatus.CREATED);
        } catch (UserService.UserCreationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userId) {
        return userService.deleteUserById(userId);
    }
    @DeleteMapping("/")
    public ResponseEntity<String> noUserIdProvided() {
        return new ResponseEntity<>("Please provide a user ID", HttpStatus.BAD_REQUEST);
    }
}