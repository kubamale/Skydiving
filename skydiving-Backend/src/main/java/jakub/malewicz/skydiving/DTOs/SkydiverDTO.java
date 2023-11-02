package jakub.malewicz.skydiving.DTOs;

public record SkydiverDTO(
        long id,
        String firstName,
        String lastName,
        double weight,
        String licence,
        String jumpType
){}
