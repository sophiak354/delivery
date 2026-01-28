package role;

public abstract class Role {
    private final String name;

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
