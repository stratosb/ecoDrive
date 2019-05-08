package stratos.apps.ecodrive.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Scoring {
    public static List<Double> allSpeeds;
    public static double totalDistance;

    // Temporary variables
    public static Date firstDateTime = null;
    public static double speed = 0;
    public static double previousSpeed = 0;
    public static Location previousLocation = null;
    public static boolean excessive_speeding = false;
    public static boolean idling = false;
    public static boolean phone_usage = false;

    // Measurements & Scoring parameters
    public static int hard_acceleration_count = 0;
    public static int harsh_breaking_count = 0;
    public static int speeding_count = 0;
    public static int phone_usage_count = 0;

    public static double excessive_speeding_distance = 0;  // > 70 km/h
    public static double idling_distance = 0;              // 0 - 10 km/h
    public static double phone_usage_distance = 0;

    public static void clearAll() {
        allSpeeds = new ArrayList<>();
        totalDistance = 0;

        firstDateTime = null;
        speed = 0;
        previousSpeed = 0;
        Location previousLocation = null;
        excessive_speeding = false;
        idling = false;
        phone_usage = false;

        hard_acceleration_count = 0;
        harsh_breaking_count = 0;
        speeding_count = 0;
        phone_usage_count = 0;

        excessive_speeding_distance = 0;
        idling_distance = 0;
        phone_usage_distance = 0;
    }
}
