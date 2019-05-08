package stratos.apps.ecodrive.model;

import com.google.gson.annotations.SerializedName;

public class Child {
    @SerializedName("ageGroup")
    private String ageGroup;

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }
}
