package jakub.malewicz.skydiving.DTOs;

import java.util.Set;

public record CredentialsDTO(String role, String token, String email, Set<String> privileges) {
}
