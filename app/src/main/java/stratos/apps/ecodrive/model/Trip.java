package stratos.apps.ecodrive.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Trip {
    @SerializedName("identifier")
    private String identifier;      // vehicle uuid
    @SerializedName("date_time")
    private Date date_time;
    @SerializedName("distance")
    private double distance;
    @SerializedName("avg_speed")
    private double avg_speed;
    @SerializedName("max_speed")
    private double max_speed;
    @SerializedName("speeding_event_number")
    private int speeding_event_number;
    @SerializedName("speeding_event_distance")
    private double speeding_event_distance;
    @SerializedName("phone_usage_event_number")
    private int phone_usage_event_number;
    @SerializedName("phone_usage_event_distance")
    private double phone_usage_event_distance;
    @SerializedName("acceleration_event_number")
    private int acceleration_event_number;
    @SerializedName("breaking_event_number")
    private int breaking_event_number;
    @SerializedName("cornering_event_number")
    private int cornering_event_number;
    @SerializedName("idling_event_number")
    private int idling_event_number;
    @SerializedName("idling_event_distance")
    private  double idling_event_distance;
    @SerializedName("total_performance")
    private  double total_performance;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(double avg_speed) {
        this.avg_speed = avg_speed;
    }

    public double getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(double max_speed) {
        this.max_speed = max_speed;
    }

    public int getSpeeding_event_number() {
        return speeding_event_number;
    }

    public void setSpeeding_event_number(int speeding_event_number) {
        this.speeding_event_number = speeding_event_number;
    }

    public double getSpeeding_event_distance() {
        return speeding_event_distance;
    }

    public void setSpeeding_event_distance(double speeding_event_distance) {
        this.speeding_event_distance = speeding_event_distance;
    }

    public int getPhone_usage_event_number() {
        return phone_usage_event_number;
    }

    public void setPhone_usage_event_number(int phone_usage_event_number) {
        this.phone_usage_event_number = phone_usage_event_number;
    }

    public double getPhone_usage_event_distance() {
        return phone_usage_event_distance;
    }

    public void setPhone_usage_event_distance(double phone_usage_event_distance) {
        this.phone_usage_event_distance = phone_usage_event_distance;
    }

    public int getAcceleration_event_number() {
        return acceleration_event_number;
    }

    public void setAcceleration_event_number(int acceleration_event_number) {
        this.acceleration_event_number = acceleration_event_number;
    }

    public int getBreaking_event_number() {
        return breaking_event_number;
    }

    public void setBreaking_event_number(int breaking_event_number) {
        this.breaking_event_number = breaking_event_number;
    }

    public int getCornering_event_number() {
        return cornering_event_number;
    }

    public void setCornering_event_number(int cornering_event_number) {
        this.cornering_event_number = cornering_event_number;
    }

    public int getIdling_event_number() {
        return idling_event_number;
    }

    public void setIdling_event_number(int idling_event_number) {
        this.idling_event_number = idling_event_number;
    }

    public double getIdling_event_distance() {
        return idling_event_distance;
    }

    public void setIdling_event_distance(double idling_event_distance) {
        this.idling_event_distance = idling_event_distance;
    }

    public double getTotal_performance() {
        return total_performance;
    }

    public void setTotal_performance(double total_performance) {
        this.total_performance = total_performance;
    }
}
