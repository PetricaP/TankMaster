package paoo.core.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject {
    private JsonObject() {
        attributes = new LinkedHashMap<>();
        indentLevel = 0;
    }

    public String toString() {
        StringBuilder repr = new StringBuilder();
        //repr.append("    ".repeat(Math.max(0, indentLevel)));
        repr.append("{\n");

        for(Map.Entry<String, Object> entry : attributes.entrySet()) {
            repr.append("\t".repeat(Math.max(0, indentLevel + 1)));

            String key = entry.getKey();
            Object value = entry.getValue();
            repr.append("\"").append(key).append("\": ");
            if(value instanceof String) {
                repr.append("\"");
            }
            repr.append(value);
            if(value instanceof String) {
                repr.append("\"");
            }
            repr.append(",\n");
        }
        repr.delete(repr.length() - 2, repr.length());
        repr.append('\n');
        repr.append("    ".repeat(Math.max(0, indentLevel))).append('}');
        return repr.toString();
    }

    private void setIndentLevel(int level) {
        indentLevel = level;
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            Object value = entry.getValue();
            if(value instanceof JsonObject) {
                ((JsonObject)value).setIndentLevel(level + 1);
            }
        }
    }

    public LinkedHashMap<String, Object> getAttributes() {
        return attributes;
    }

    public static class Builder {
        JsonObject objectBuilt;

        Builder() {
            objectBuilt = new JsonObject();
        }

        public Builder addAttribute(String key, Object value) {
            objectBuilt.attributes.put(key, value);
            if(value instanceof JsonObject) {
                ((JsonObject)value).setIndentLevel(objectBuilt.indentLevel + 1);
            }
            return this;
        }

        public JsonObject getObject() {
            return objectBuilt;
        }
    }

    public String getString(String key) {
        return (String)attributes.get(key);
    }

    public int getInt(String key) {
        return (int)attributes.get(key);
    }

    public float getFloat(String key) {
        return (float)attributes.get(key);
    }

    public JsonObject getJsonObject(String key) {
        return (JsonObject)attributes.get(key);
    }

    public static Builder build() {
        return new Builder();
    }

    private int indentLevel;
    private LinkedHashMap<String, Object> attributes;
}
