package org.example.tools;

public enum TypeOfSearch {
    MASK("mask"),
    FILE_NAME("name"),
    REGEX("regex");

    private String text;

    TypeOfSearch(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TypeOfSearch fromString(String text) {
        for (TypeOfSearch type : TypeOfSearch.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}