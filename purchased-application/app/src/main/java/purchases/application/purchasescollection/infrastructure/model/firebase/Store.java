package purchases.application.purchasescollection.infrastructure.model.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Store {

    public String id;
    public String name;
    public String description;
    public float latitude;
    public float longitude;
    public double radius;
    public String uuid;


    public Store() {
    }

    public Store(String id, String name, String description, float latitude, float longitude, double radius, String uuid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.uuid = uuid;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("name", name);
        result.put("description", description);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("radius", radius);
        result.put("uuid", uuid);

        return result;
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

    public String getUuid() {
        return uuid;
    }

   public boolean isEmpty() {
        return id == null && name == null;
   }
}
