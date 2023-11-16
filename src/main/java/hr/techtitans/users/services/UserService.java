package hr.techtitans.users.services;


import hr.techtitans.users.dtos.UserDto;
import hr.techtitans.users.models.User;
import hr.techtitans.users.models.UserRole;
import hr.techtitans.users.models.UserStatus;
import hr.techtitans.users.repositories.UserRepository;
import hr.techtitans.users.repositories.UserRoleRepository;
import hr.techtitans.users.repositories.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

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
}
