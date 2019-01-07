package purchases.application.purchasescollection.infrastructure.model.dto;

import com.google.android.gms.maps.model.LatLng;

public class StoreDto {

    private String id;
    private String name;
    private String description;

    private float latitude;
    private float longitude;
    private double radius;

    private MarkerDto marker;
    private TrackDto track;

    public StoreDto(String id, String name, String description, float latitude, float longitude, double radius) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }


    public LatLng getPosition() {
        return new LatLng(this.latitude, this.longitude);
    }

    public MarkerDto getMarker() {

        return new MarkerDto(name, latitude, longitude);
    }

    public TrackDto getTrack() {

        return new TrackDto(name, latitude, longitude, radius);
    }

    public String getTextRadius() {
        String radiusText = Double.toString(radius);

        return radiusText + " m";
    }

    public String getTextPosition() {

        return "Store location of latitude: " + String.valueOf(latitude) + " and longitude: " + String.valueOf(longitude);
    }
}
