package com.iot.model.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Double latitude;
    private Double longitude;
    private String address;
    
    public boolean isValid() {
        return latitude != null && longitude != null &&
               latitude >= -90 && latitude <= 90 &&
               longitude >= -180 && longitude <= 180;
    }
}