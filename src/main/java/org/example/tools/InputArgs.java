package org.example.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class InputArgs {
    private Path dir;
    private String file;
    private TypeOfSearch typeSearch;
    private File outFileName;

    @Override
    public String toString() {
        return "InputArgs{"
                + "dir=" + dir
                + ", file='" + file + '\''
                + ", typeSearch=" + typeSearch
                + ", outFileName=" + outFileName
                + '}';
    }

    public void validate(String[] args) throws IllegalArgumentException, IOException {
        ArgsName argsName = ArgsName.of(args);

        dir = Paths.get(argsName.get("d"));
        if (!Files.exists(dir)) {
            String error = String.format("Error: The directory \"%s\" specified in the arguments does not exist", dir.toAbsolutePath());
            throw new IllegalArgumentException(error);
        }
        file = argsName.get("n");
        typeSearch = TypeOfSearch.fromString(argsName.get("t"));

        outFileName = new File(argsName.get("o"));
        File dirParent = outFileName.getParentFile();
        if (!dirParent.exists()) {
            dirParent.mkdirs();
        }
        if (!outFileName.exists()) {
            outFileName.createNewFile();
        }
    }

    public Predicate<Path> getCondition() {
        return switch (typeSearch) {
            case MASK -> path -> {
                String pattern = file.replaceAll("[.]", "[.]");
                pattern = pattern.replaceAll("[*]", ".*");
                pattern = pattern.replaceAll("[?]", ".?");
                return Pattern.compile(pattern).matcher(path.getFileName().toString()).find();
            };
            case REGEX -> path -> Pattern.compile(file).matcher(path.getFileName().toString()).find();
            case FILE_NAME -> path -> file.equalsIgnoreCase(path.getFileName().toString());
            default -> throw new IllegalArgumentException("Failed to create a search condition");
        };
    }

    public Path getDir() {
        return dir;
    }

    public String getFile() {
        return file;
    }

    public TypeOfSearch getTypeSearch() {
        return typeSearch;
    }

    public File getOutFileName() {
        return outFileName;
    }
}
