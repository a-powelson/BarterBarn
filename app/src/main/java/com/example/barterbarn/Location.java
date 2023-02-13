package com.example.barterbarn;

public class Location {
    private final String city;
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    /**
     * Calculates the distance between two points in kilometers.
     * Uses the haversine method https://stackoverflow.com/a/16794680.
     *
     * @return distance between src and dest in kilometers, rounded to two decimal places
     */
    public static double calculateDistance(Location src, Location dest) {
        final int earthRadius = 6371;

        double latitudeDistance = Math.toRadians(dest.latitude - src.latitude);
        double longitudeDistance = Math.toRadians(dest.longitude - src.longitude);

        double a = Math.sin(latitudeDistance / 2)
                * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(src.latitude))
                * Math.cos(Math.toRadians(dest.latitude))
                * Math.sin(longitudeDistance / 2)
                * Math.sin(longitudeDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInKm = earthRadius * c;

        return Math.round(distanceInKm * 100.0) / 100.0;
    }

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }
}
