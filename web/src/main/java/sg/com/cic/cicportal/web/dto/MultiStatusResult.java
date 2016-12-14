package sg.com.cic.cicportal.web.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultiStatusResult<T> {
    private final HttpStatus status;
    private final String key;
    private final T value;

}
