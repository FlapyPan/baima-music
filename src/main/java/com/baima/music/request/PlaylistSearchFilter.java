package com.baima.music.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlaylistSearchFilter extends BaseSearchFilter {
    private String name = "";
    private Boolean recommended;
    private Boolean special;
}
