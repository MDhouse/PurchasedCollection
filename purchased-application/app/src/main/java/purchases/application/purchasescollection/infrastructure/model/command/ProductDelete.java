package purchases.application.purchasescollection.infrastructure.model.command;

public class ProductDelete {

    private final String id;

    public ProductDelete(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
