package util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class ObjectWithDynamicAttributes {
    private Map<Object, Object> attributes = new LinkedHashMap<>();

    public String getTypeDescription() {
        return getClass().getSimpleName();
    }

    public String getAttributesDescription() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append('{');
        boolean first = true;
        for (Object key : attributes.keySet()) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(", ");
            }

            stringBuilder.append(key)
                    .append('=')
                    .append(attributes.get(key));
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }

    public Set<Object> getKeySet() {
        return Collections.unmodifiableSet(attributes.keySet());
    }

    public Object getAttribute(Object key) {
        return attributes.get(key);
    }

    public void setAttribute(Object key, Object value) {
        attributes.put(key, value);
    }

    public void removeAttribute(Object key) {
        attributes.remove(key);
    }

    public ObjectWithDynamicAttributes getCopy() {
        ObjectWithDynamicAttributes copy = null;

        try {
            copy = getClass().newInstance();
            copy.attributes.putAll(attributes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass()
                && attributes.equals(((ObjectWithDynamicAttributes) o).attributes);
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    @Override
    public String toString() {
        return getTypeDescription() + getAttributesDescription();
    }
}
