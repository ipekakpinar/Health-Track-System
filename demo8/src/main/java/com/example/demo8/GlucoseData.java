package com.example.demo8;


/**
 * GlucoseData sınıfı, kan şekeri ölçümlerini temsil eden bir sınıftır.
 */

public class GlucoseData {
    private String glucoseMeasurementDate;
    private int glucoseValue;

    /**
     * GlucoseData sınıfının yapıcı metodu.
     *
     * @param glucoseMeasurementDate Kan şekeri ölçüm tarihi
     * @param glucoseValue           Kan şekeri değeri
     */
    public GlucoseData(String glucoseMeasurementDate, int glucoseValue) {
        this.glucoseMeasurementDate = glucoseMeasurementDate;
        this.glucoseValue = glucoseValue;
    }

    /**
     * Getter ve Setterlar:
     */
    public String getGlucoseMeasurementDate() {
        return glucoseMeasurementDate;
    }

    public int getGlucoseValue() {
        return glucoseValue;
    }
}
