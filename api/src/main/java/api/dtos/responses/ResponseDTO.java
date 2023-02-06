package api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private T data;
    private List<String> messages;
    private List<String> errors;

    @JsonProperty
    public Boolean hasMessages() {
        return Boolean.TRUE.equals(Objects.nonNull(this.messages) && !this.messages.isEmpty());
    }

    @JsonProperty
    public Boolean hasErrors() {
        return Boolean.TRUE.equals(Objects.nonNull(this.errors) && !this.errors.isEmpty());
    }
}
