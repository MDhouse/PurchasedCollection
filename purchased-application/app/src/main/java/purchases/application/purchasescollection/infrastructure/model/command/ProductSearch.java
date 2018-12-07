package purchases.application.purchasescollection.infrastructure.model.command;

public class ProductSearch {

    private final String id;
    private final String name;

    public ProductSearch(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
