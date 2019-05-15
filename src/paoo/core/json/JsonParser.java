package paoo.core.json;

import java.util.Stack;

public class JsonParser {
    enum TokenType {
        INT,
        FLOAT,
        STRING,
        NONE
    }

    public static JsonObject parse(String s) {
        int i = 0;
        JsonObject.Builder builder = null;
        Stack<JsonObject.Builder> objectStack = new Stack<>();
        Stack<String> nameStack = new Stack<>();

        StringBuilder name = new StringBuilder();
        StringBuilder value = new StringBuilder();
        TokenType currentType;
        while(i < s.length()) {
            currentType = TokenType.NONE;
            while(Character.isWhitespace(s.charAt(i))) {
                ++i;
            }
            if(s.charAt(i) == '{') {
                if(builder != null) {
                    objectStack.push(builder);
                    nameStack.push(name.toString());
                }
                builder = JsonObject.build();
                ++i;
                continue;
            }
            name.delete(0, name.length());
            value.delete(0, value.length());
            if(s.charAt(i) == '"') {
                ++i;
                while(s.charAt(i) != '"') {
                    name.append(s.charAt(i));
                    ++i;
                }
                ++i;
            }
            while(Character.isWhitespace(s.charAt(i))) {
                ++i;
            }
            if(s.charAt(i) == ':') {
                ++i;
                while(Character.isWhitespace(s.charAt(i))) {
                    ++i;
                }
                if(s.charAt(i) == '"') {
                    ++i;
                    currentType = TokenType.STRING;
                    while(s.charAt(i) != '"') {
                        value.append(s.charAt(i));
                        ++i;
                    }
                    ++i;
                    if(s.charAt(i) == ',') {
                        ++i;
                    }
                } else if(s.charAt(i) == '-' || Character.isDigit(s.charAt(i))) {
                    currentType = TokenType.INT;
                    while(!Character.isWhitespace(s.charAt(i)) && s.charAt(i) != ',') {
                        if(s.charAt(i) == '.') {
                            currentType = TokenType.FLOAT;
                        }
                        value.append(s.charAt(i));
                        ++i;
                    }
                    if(s.charAt(i) == ',') {
                        ++i;
                    }
                }
            }
            switch(currentType) {
                case STRING:
                    assert builder != null;
                    builder.addAttribute(name.toString(), value.toString());
                    break;
                case INT:
                    assert builder != null;
                    builder.addAttribute(name.toString(), Integer.parseInt(value.toString()));
                    break;
                case FLOAT:
                    assert builder != null;
                    builder.addAttribute(name.toString(), Float.parseFloat(value.toString()));
                    break;
            }
            if(s.charAt(i) == '}') {
                if(!objectStack.empty()) {
                    JsonObject.Builder parent = objectStack.pop();
                    assert builder != null;
                    parent.addAttribute(nameStack.pop(), builder.getObject());
                    builder = parent;
                }
                ++i;
                if(i < s.length() && s.charAt(i) == ',') {
                    ++i;
                }
            }
        }

        assert builder != null;
        return builder.getObject();
    }
}
