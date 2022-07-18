import java.util.HashMap;

public class KeyRegistry {

    private HashMap<String, String> map = new HashMap<>();

    public KeyRegistry() {
    }

    public void add(Class c, String key) {
        map.put(c.getName(), key);
    }

    public String get(Class c) {
        return map.get(c.getName());
    }

}