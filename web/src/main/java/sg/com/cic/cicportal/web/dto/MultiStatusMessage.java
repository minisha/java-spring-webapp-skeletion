package sg.com.cic.cicportal.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultiStatusMessage<T> {
    private final List<MultiStatusResult<T>> results;
}
