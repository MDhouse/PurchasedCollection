package purchases.application.purchasescollection.infrastructure.model.dto;

public class MarkerDto {

    private String name;
    private float latitude;
    private float longitude;

    public MarkerDto(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
