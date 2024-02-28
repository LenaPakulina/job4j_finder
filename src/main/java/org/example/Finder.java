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
        try {
            finder.getInputArgs().validate(args);
        } catch (IllegalArgumentException e) {
            System.out.println("The necessary parameters are not defined. For more information, see the README.");
            throw e;
        }
        FileVisitor fileVisitor = new FileVisitor(finder.getInputArgs().getCondition());
        Files.walkFileTree(finder.getInputArgs().getDir(), fileVisitor);

        try (PrintStream stream = new PrintStream(new FileOutputStream(finder.getInputArgs().getOutFileName()))) {
            fileVisitor.getPaths().forEach(stream::println);
        } catch (IOException e) {
            e.printStackTrace();
            fileVisitor.getPaths().forEach(System.out::println);
        }
    }
}
