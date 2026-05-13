package Managers;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<Integer, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public boolean canBook(User user){
        return !user.getOnRide();
    }

    public User addUser(String name){
        User user = new User(name);
        users.put(user.getId(),user);
        System.out.println("User successfully added, id: " + user.getId() + " name: " + name);
        return user;
    }

    public boolean userExists(int id){
        return users.containsKey(id);
    }

    public void startUserRide(User user){
        user.startRide();
    }
    public void endUserRide(User user){
        user.completeRide();
    }
}
