package org.example;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FileVisitor extends SimpleFileVisitor<Path> {
    private Predicate<Path> condition;
    private final List<Path> paths = new ArrayList<>();

    public FileVisitor(Predicate<Path> condition) {
        this.condition = condition;
    }

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attributes) throws IOException {
        System.out.println(file);
        if (condition.test(file)) {
            paths.add(file);
        }
        return super.visitFile(file, attributes);
    }

    public List<Path> getPaths() {
        return paths;
    }
}
