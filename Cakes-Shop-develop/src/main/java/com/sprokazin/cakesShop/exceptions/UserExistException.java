package com.sprokazin.cakesShop.exceptions;

public class UserExistException extends Exception{
    public UserExistException(String message) {
        super(message);
    }
}