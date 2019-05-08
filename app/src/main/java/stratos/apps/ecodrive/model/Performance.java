package stratos.apps.ecodrive.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Performance {
    @SerializedName("identifier")
    private String identifier;      // vehicle uuid
    @SerializedName("total_performance")
    private double total_performance;
    @SerializedName("safety")
    private double safety;
    @SerializedName("economy")
    private double economy;
    @SerializedName("speed")
    private double speed;
    @SerializedName("focus")
    private double focus;
    @SerializedName("phone_usage")
    private double phone_usage;
    @SerializedName("acceleration")
    private double acceleration;
    @SerializedName("breaking")
    private double breaking;
    @SerializedName("cornering")
    private double cornering;
    @SerializedName("excessive_speeding")
    private double excessive_speeding;
    @SerializedName("speeding")
    private double speeding;
    @SerializedName("idling")
    private double idling;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getTotal_performance() {
        return total_performance;
    }

    public void setTotal_performance(double total_performance) {
        this.total_performance = total_performance;
    }

    public double getSafety() {
        return safety;
    }

    public void setSafety(double safety) {
        this.safety = safety;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFocus() {
        return focus;
    }

    public void setFocus(double focus) {
        this.focus = focus;
    }

    public double getPhone_usage() {
        return phone_usage;
    }

    public void setPhone_usage(double phone_usage) {
        this.phone_usage = phone_usage;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getBreaking() {
        return breaking;
    }

    public void setBreaking(double breaking) {
        this.breaking = breaking;
    }

    public double getCornering() {
        return cornering;
    }

    public void setCornering(double cornering) {
        this.cornering = cornering;
    }

    public double getExcessive_speeding() {
        return excessive_speeding;
    }

    public void setExcessive_speeding(double excessive_speeding) {
        this.excessive_speeding = excessive_speeding;
    }

    public double getSpeeding() {
        return speeding;
    }

    public void setSpeeding(double speeding) {
        this.speeding = speeding;
    }

    public double getIdling() {
        return idling;
    }

    public void setIdling(double idling) {
        this.idling = idling;
    }
}
