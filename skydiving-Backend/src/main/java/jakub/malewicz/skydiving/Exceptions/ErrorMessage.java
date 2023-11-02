package jakub.malewicz.skydiving.Exceptions;


import java.util.Date;

public record ErrorMessage(int statusCode, Date timestamp, String message, String description) {
}
