package jakub.malewicz.skydiving.DTOs;

import java.util.Optional;
import java.util.Set;

public record ApprovedDTO(String email, String role, String licence, Optional<Set<String>> privileges){}
