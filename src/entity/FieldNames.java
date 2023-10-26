package entity;

public enum FieldNames {
    MARKED("Marked"),
    MARKED_REVEALED("Marked"),
    MINE("Mine"),
    NEW("New"),
    OPEN("Open");

    private final String name;

    FieldNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
