package stratos.apps.ecodrive.model;

import com.google.gson.annotations.SerializedName;

public class Vehicle {
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("model")
    private String model;
    @SerializedName("age")
    private String age;
    @SerializedName("fuelType")
    private String fuelType;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
