package org.example;

import org.example.tools.InputArgs;
import java.io.IOException;
import java.nio.file.Files;

public class Finder {
    private final InputArgs inputArgs = new InputArgs();

    public InputArgs getInputArgs() {
        return inputArgs;
    }

    public static void main(String[] args) throws IllegalArgumentException, IOException {
        Finder finder = new Finder();
        finder.getInputArgs().validate(args);
        System.out.println(finder.getInputArgs());

        FileVisitor fileVisitor = new FileVisitor(finder.getInputArgs().getCondition());
        Files.walkFileTree(finder.getInputArgs().getDir(), fileVisitor);
        System.out.println(fileVisitor.getPaths());
    }
}
