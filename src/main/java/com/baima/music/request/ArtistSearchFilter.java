package com.baima.music.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArtistSearchFilter extends BaseSearchFilter {
    private String name;
    private Boolean recommended;
}
