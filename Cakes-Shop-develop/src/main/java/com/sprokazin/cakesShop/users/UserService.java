package com.sprokazin.cakesShop.users;

import com.sprokazin.cakesShop.exceptions.UserExistException;
import com.sprokazin.cakesShop.rest.dto.user.User;

import java.util.List;

public interface UserService {

    void addUser(User user) throws UserExistException;

    List<User> getUsers();

    Long getUserId(String number);
}
