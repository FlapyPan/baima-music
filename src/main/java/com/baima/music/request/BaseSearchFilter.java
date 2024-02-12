package com.baima.music.request;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public abstract class BaseSearchFilter {
    @Min(value = 1, message = "page最小值为1")
    private Integer page = 1;
    @Min(value = 0, message = "page最小值为1")
    private Integer size = 10;
    private Sort.Direction direction = Sort.Direction.DESC;
    private final List<String> sortBy;

    public BaseSearchFilter() {
        sortBy = new ArrayList<>();
    }

    public Pageable toPageable() {
        sortBy.add("createTime");
        var orders = sortBy.stream()
                .map(s -> new Sort.Order(direction, s))
                .collect(Collectors.toList());
        return PageRequest.of(page - 1, size, Sort.by(orders));
    }
}
