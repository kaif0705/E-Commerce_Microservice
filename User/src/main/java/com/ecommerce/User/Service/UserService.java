package com.ecommerce.User.Service;

import com.ecommerce.User.Model.Address;
import com.ecommerce.User.Model.User;
import com.ecommerce.User.Payload.AddressDTO;
import com.ecommerce.User.Payload.UserRequest;
import com.ecommerce.User.Payload.UserResponse;
import com.ecommerce.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }


    public void addUser(UserRequest userRequest){
        User user = new User();
        User userAdded= updateUserFromRequest(user, userRequest);
        userRepository.save(userAdded);
    }

    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private User updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setZipcode(userRequest.getZipcode());

        if (userRequest.getAddress() != null) {
            AddressDTO requestAddressDto = userRequest.getAddress(); // For clarity

            Address addressToUpdate = user.getAddress(); // Get existing address from user
            if (addressToUpdate == null) {
                // If user doesn't have an address, create a new one
                addressToUpdate = new Address();
            }
            // else: user already has an address, we will update the existing object

            // Map fields from request DTO to the Address entity
            addressToUpdate.setStreet(requestAddressDto.getStreet());
            addressToUpdate.setCity(requestAddressDto.getCity());
            addressToUpdate.setBuildingName(requestAddressDto.getBuildingName()); // Don't forget this!
            addressToUpdate.setState(requestAddressDto.getState());
            addressToUpdate.setCountry(requestAddressDto.getCountry());
            addressToUpdate.setZipcode(requestAddressDto.getZipcode());

            user.setAddress(addressToUpdate); // Set the (new or updated) address to the user
        }

        return user;
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}
