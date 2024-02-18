package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.UserValidator;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.create(user);
    }

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("Контакт не найден");
    }

    public List<User> readAll() {
        return repository.findAll();
    }

    public void updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public void deleteUser(String userId) {
        repository.delete(Long.parseLong(userId));
    }

    public User createUser() {
        UserValidator validator = new UserValidator();
        Scanner in = new Scanner(System.in);
        
        String firstName = prompt(in, "Имя: ");
        String lastName = prompt(in, "Фамилия: ");
        String phone = prompt(in, "Номер телефона: ");
        
        return validator.validate(new User(firstName, lastName, phone));
    }

    private String prompt(Scanner in, String message) {
        System.out.print(message);
        return in.nextLine();
    }
}
