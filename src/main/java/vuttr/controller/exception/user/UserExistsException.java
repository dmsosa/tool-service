package vuttr.controller.exception.user;

public class UserExistsException extends Exception {
    private String message;
    public UserExistsException(Long id) {
        this.message = String.format("User with id %s already exists", String.valueOf(id));
    }
    public UserExistsException(String login) {

        if (login.contains("@")) {
            this.message = String.format("User with email %s already exists", login);
        } else {
            this.message = String.format("User with username %s already exists", login);
        }
    }

    public static UserExistsException createWith(Long id) {
        return new UserExistsException(id);
    }
    public static UserExistsException createWith(String login) {
        return new UserExistsException(login);
    }

    public String getMessage() {
        return this.message;
    }

}
