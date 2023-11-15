package vuttr.domain.user;

public record RegisterDTO(String username, String email, String password, UserRole role) {
}
