public class Item {

    public final String label;
    public final String code;


    public Item(String label, String code) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String toString() {
        return "label: " + label + ", " + "code: " + code;
    }
}