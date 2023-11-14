package com.a504.qookie.domain.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeoRequest {
    @JsonProperty("acc")
    private double acc;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("heading")
    private double heading;
    @JsonProperty("spd")
    private double spd;


    public String toString() {
        return "ACC: " + gr.acc + " LAT: " + gr.lat + " LON: " + gr.lon + " HEAD: " + gr.heading + " SPD: " + gr.spd;
    }
}
