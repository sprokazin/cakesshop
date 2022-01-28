package com.sprokazin.cakesShop.users;

import com.sprokazin.cakesShop.exceptions.UserExistException;
import com.sprokazin.cakesShop.rest.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) throws UserExistException {
        if(userRepository.existsUserEntityByNumber(user.getNumber())) {
            throw new UserExistException("User is already exist");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setNumber(user.getNumber());
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream().map(userTmp -> {
            User user = new User();
            user.setName(userTmp.getName());
            user.setNumber(userTmp.getNumber());
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public Long getUserId(String number) {
        return userRepository.findUserEntityByNumber(number).getId();
    }
}
