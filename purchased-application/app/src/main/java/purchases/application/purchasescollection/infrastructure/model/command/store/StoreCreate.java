package purchases.application.purchasescollection.infrastructure.model.command.store;

import java.util.UUID;

import purchases.application.purchasescollection.infrastructure.model.firebase.Store;

public class StoreCreate {

    private String name;
    private String description;
    private float latitude;
    private float longitude;
    private double radius;
    private String uuid;

    public StoreCreate(String name, String description, float latitude, float longitude, double radius, String uuid) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }

    public Store toFirebase() {
        return new Store(
                UUID.randomUUID().toString(),
                name,
                description,
                latitude,
                longitude,
                radius,
                uuid
        );
    }
}
