package com.akks.event.planner.service;

import com.akks.event.planner.model.User;
import com.akks.event.planner.model.UserRole;
import com.akks.event.planner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        User user1 = new User("Alice", "alice@example.com", "password123", UserRole.USER);
        User user2 = new User("Bob", "bob@example.com", "password456", UserRole.ADMIN);
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void getUserById() {
        User user1 = new User("Alice", "alice@example.com", "password123", UserRole.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> actualUser = userService.getUserById(1L);

        assertTrue(actualUser.isPresent(), "User should be present");
        assertEquals(user1, actualUser.get(), "User should match the expected user");
    }


    @Test
    void createUser() {
        User user1 = new User("Alice", "alice@example.com", "password123", UserRole.USER);
        when(userRepository.save(user1)).thenReturn(user1);

        User createdUser = userService.createUser(user1);

        assertNotNull(createdUser, "User should be created and not null");
        assertEquals(user1, createdUser, "Created user should match the provided user");
        verify(userRepository).save(user1);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}