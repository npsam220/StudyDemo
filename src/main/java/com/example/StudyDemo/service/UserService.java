package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.User;
import com.example.StudyDemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User>  getByName(String name) {
        return userRepository.findByName(name);
    }
    public List<User> getByNameAndAge(String name, Integer age) {
        return userRepository.findByNameAndAge(name, age);
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public List<User> searchByName(String name) {
        return userRepository.findByNameContaining(name);
    }
    public List<User> searchByNameByJPQL(String name) {
        return userRepository.searchByNameByJPQL(name);
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

