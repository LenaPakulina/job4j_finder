package org.example;

import org.example.tools.InputArgs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

public class Finder {
    private final InputArgs inputArgs = new InputArgs();

    public InputArgs getInputArgs() {
        return inputArgs;
    }

    public static void main(String[] args) throws IllegalArgumentException, IOException {
        Finder finder = new Finder();
        finder.getInputArgs().validate(args);
        FileVisitor fileVisitor = new FileVisitor(finder.getInputArgs().getCondition());
        Files.walkFileTree(finder.getInputArgs().getDir(), fileVisitor);

        try (PrintStream stream = new PrintStream(new FileOutputStream(finder.getInputArgs().getOutFileName()))) {
            fileVisitor.getPaths().forEach(stream::println);
        }
    }
}
