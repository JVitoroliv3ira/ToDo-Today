package api.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO<T> {
    private List<T> content;
    private Integer page;
    private Integer pageSize;
    private Long total;
}
