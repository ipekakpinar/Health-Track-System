package com.example.demo8;

/**
 * Tansiyon verilerini temsil eden sınıftır.
 * Her ölçüm, ölçüm tarihi ve tansiyon değerini içerir.
 */

public class BloodPressureData {
    private String bloodPressureMeasurementDate;
    private int bloodPressureValue;



    /**BloodPressureData sınıfının constructorıdır.
     * @param measurementDate       Tansiyon ölçüm tarihi
     * @param bloodPressureValue    Tansiyon değeri
     */
    public BloodPressureData(String measurementDate, int bloodPressureValue) {
        this.bloodPressureMeasurementDate = measurementDate;
        this.bloodPressureValue = bloodPressureValue;
    }



    /**
     * measurementDate(String) ve bloodPressureValue(int) parametrelerinin getter ve setterları:
     * */
    public String getBloodPressureMeasurementDate() {
        return bloodPressureMeasurementDate;
    }

    public int getBloodPressureValue() {
        return bloodPressureValue;
    }
}
