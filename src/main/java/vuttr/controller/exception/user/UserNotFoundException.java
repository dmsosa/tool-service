package vuttr.controller.exception.user;

public class UserNotFoundException extends Exception {
    private String message;
    public UserNotFoundException(Long id) {
        this.message = String.format("User with id %s was not found", String.valueOf(id));
    }
    public UserNotFoundException(String login) {
        this.message = String.format("User with login %s was not found", login);
    }

    public static UserNotFoundException createWith(Long id) {
        return new UserNotFoundException(id);
    }
    public static UserNotFoundException createWith(String login) {
        return new UserNotFoundException(login);
    }

    public String getMessage() {
        return this.message;
    }
}
