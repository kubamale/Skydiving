package jakub.malewicz.skydiving.DTOs;

import java.util.Set;

public record ApprovedDTO(String email, String role, String licence, Set<String> privileges){}
