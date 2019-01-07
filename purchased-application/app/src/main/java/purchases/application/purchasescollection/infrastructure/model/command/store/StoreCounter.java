package purchases.application.purchasescollection.infrastructure.model.command.store;

public class StoreCounter {

    private String id;
    private double newRadius;

    public StoreCounter(String id, double newRadius) {
        this.id = id;
        this.newRadius = newRadius;

    }

    public String getId() {
        return id;
    }

    public double getNewRadius() {
        return newRadius;
    }
}
