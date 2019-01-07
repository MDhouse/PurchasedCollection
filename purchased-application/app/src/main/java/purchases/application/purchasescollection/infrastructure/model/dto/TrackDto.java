package purchases.application.purchasescollection.infrastructure.model.dto;

public class TrackDto {

    private String name;
    private float latitude;
    private float longitude;
    private float radius;

    public TrackDto(String name, float latitude, float longitude, double radius) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = (float)radius;
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

    public float getRadius() {
        return radius;
    }
}
