package com.baima.music.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MusicSearchFilter extends BaseSearchFilter {
    private String name;
}
