package it.lo.exp.weander.util;

import java.util.Random;

public class LocationUtil {

    private static final double EARTH_RADIUS = 6371000.0; // metres
    private static final Random RANDOM = new Random();

    /**
     * Returns a random lat/lng within [minRadius, maxRadius] metres of the given origin.
     */
    public static double[] randomNearbyPoint(double lat, double lng, int minRadius, int maxRadius) {
        double distance = minRadius + RANDOM.nextDouble() * (maxRadius - minRadius);
        double angle = RANDOM.nextDouble() * 2 * Math.PI;
        double dLat = (distance * Math.cos(angle)) / EARTH_RADIUS;
        double dLng = (distance * Math.sin(angle)) / (EARTH_RADIUS * Math.cos(Math.toRadians(lat)));
        return new double[]{lat + Math.toDegrees(dLat), lng + Math.toDegrees(dLng)};
    }

    /** Haversine distance in metres between two points. */
    public static double distanceMeters(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return EARTH_RADIUS * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    /** Approximate walking time at 5 km/h. */
    public static int walkingMinutes(double distanceMeters) {
        return Math.max(1, (int) Math.round(distanceMeters / 83.33));
    }

    public static String formatDistance(double distanceMeters) {
        if (distanceMeters < 1000) {
            return Math.round(distanceMeters) + " m";
        } else {
            return String.format("%.1f km", distanceMeters / 1000);
        }
    }
}
