package jakub.malewicz.skydiving.DTOs;


public record RegisterDTO(String firstName, String lastName, String email, String phone, String emergencyPhone, double weight, String password, String licence, String role){
}
