package com.geomap.sight.web.dto.request;

import com.geomap.sight.domain.Coordinates;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FindSightsRequest(
        @NotNull
        Coordinates angle1,
        @NotNull
        Coordinates angle2,
        @NotNull
        Coordinates angle3,
        @NotNull
        Coordinates angle4
) {
}
