package api.dtos.responses;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO<T> {
    public PageableDTO(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getPageable().getPageNumber();
        this.pageSize = page.getContent().size();
        this.total = page.getTotalElements();
    }

    private List<T> content;
    private Integer page;
    private Integer pageSize;
    private Long total;
}
