package org.example.tools;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private final Map<String, String> args = new HashMap<>();

    public String get(String key) {
        if (!args.containsKey(key)) {
            throw new IllegalArgumentException(String.format("This key: '%s' is missing", key));
        }
        return args.get(key);
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        for (String arg : args) {
            if (!arg.startsWith("-")) {
                String error = String.format("Error: This argument '%s' does not start with a '-' character", arg);
                throw new IllegalArgumentException(error);
            }
            if (!arg.contains("=")) {
                String error = String.format("Error: This argument '%s' does not contain an equal sign", arg);
                throw new IllegalArgumentException(error);
            }
            if (arg.indexOf("=") == (arg.length() - 1)) {
                String error = String.format("Error: This argument '%s' does not contain a value", arg);
                throw new IllegalArgumentException(error);
            }
            String key = arg.substring(1, arg.indexOf("="));
            String value = arg.substring(arg.indexOf("=") + 1);
            if (key.isBlank()) {
                String error = String.format("Error: This argument '%s' does not contain a key", arg);
                throw new IllegalArgumentException(error);
            }
            if (value.isBlank()) {
                String error = String.format("Error: This argument '%s' does not contain a value", arg);
                throw new IllegalArgumentException(error);
            }
            this.args.put(key, value);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-d=c", "-t=mask", "-n=*.?xt", "-o=data/log.txt"});
        System.out.println(jvm.get("d"));
        System.out.println(jvm.get("t"));
        System.out.println(jvm.get("n"));
        System.out.println(jvm.get("o"));

        ArgsName simple = ArgsName.of(args);
        System.out.println(simple.get("d"));
        System.out.println(simple.get("t"));
        System.out.println(simple.get("n"));
        System.out.println(simple.get("o"));
    }
}

