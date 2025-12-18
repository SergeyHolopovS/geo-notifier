package com.geomap.utils;

import com.geomap.sight.domain.Coordinates;
import com.geomap.sight.domain.SightEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBUtils {

    public List<SightEntity> sightsFactory(Integer amount) {
        List<SightEntity> result = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            result.add(
                SightEntity.builder()
                    .objectName("Object " + i)
                    .registryNumber(i*100000)
                    .fullAddress(i + " Lotsmanskay street c.Saint-Petersburg")
                    .cords(
                        new Coordinates(
                            (double) i, (double) i
                        )
                    )
                    .regionName("Saint-Petersburg")
                    .category("Достопримечательность")
                    .type("Памятник")
                    .description("Very interesting description")
                    .creationYear(1706)
                    .imageUrl("https://i.pinimg.com/736x/16/1e/3d/161e3d6e60b5e42ebca673b941eba662.jpg")
                    .build()
            );
        }
        return result;
    }

}
