package product.dp.io.sdk.reflection.sample;

public class JavaPerson {

    private int id = -1;
    public String name = "Unknown";

    public JavaPerson() {
        System.out.println(("Create JavaPerson..."));
    }

    @Override
    public String toString() {
        return "Person: id=" + this.id + ", name=" + this.name;
    }

    static public String staticMethod() {
        System.out.println(("staticMethod()"));
        return "staticMethod";
    }
}