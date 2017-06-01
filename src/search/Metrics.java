package search;

import java.util.Hashtable;
import java.util.Set;
import java.util.TreeMap;

public class Metrics {
    private Hashtable<String, String> hash;

    public Metrics() {
        this.hash = new Hashtable<>();
    }

    public void set(String name, int i) {
        hash.put(name, Integer.toString(i));
    }

    public void set(String name, double d) {
        hash.put(name, Double.toString(d));
    }

    public void set(String name, long l) {
        hash.put(name, Long.toString(l));
    }

    public String get(String name) {
        return hash.get(name);
    }

    public int getInt(String name) {
        String value = hash.get(name);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public double getDouble(String name) {
        String value = hash.get(name);
        return value != null ? Double.parseDouble(value) : Double.NaN;
    }

    public long getLong(String name) {
        String value = hash.get(name);
        return value != null ? Long.parseLong(value) : 0l;
    }

    public void incrementInt(String name) {
        set(name, getInt(name) + 1);
    }

    public Set<String> keySet() {
        return hash.keySet();
    }

    public String toString() {
        TreeMap<String, String> map = new TreeMap<>(hash);
        return map.toString();
    }
}
