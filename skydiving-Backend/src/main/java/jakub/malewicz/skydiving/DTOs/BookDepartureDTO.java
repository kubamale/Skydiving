package jakub.malewicz.skydiving.DTOs;

import java.util.Optional;

public record BookDepartureDTO(
        String skydiverEmail,
        long departureId,
        String jumpType,
        Optional<String> secondJumperEmail
){}
