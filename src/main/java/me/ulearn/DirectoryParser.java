package me.ulearn;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DirectoryParser {

    private String path;
    private String parentPath;
    private List<FileObject> contents;

    public DirectoryParser(String path){
        this.path = path;
        this.contents = readContents();
        this.parentPath = getParentPath();
    }

    public String getParentPath() {
        return this.path == "/" ? null : new File(path).getAbsoluteFile().getParent();
    }

    private List<FileObject> readContents() {

        List<FileObject> result = new ArrayList<FileObject>();
        try {
            File root = new File(this.path);
            File[] list = root.listFiles();
            Arrays.sort(list, Comparator.comparing(File::isDirectory).reversed());

            for ( File file : list ) {
                result.add(
                        new FileObject(
                                file.getName(),
                                file.length(),
                                getCreationDate(file.getAbsolutePath()),
                                (file.isDirectory() ? FileObject.Type.Directory : FileObject.Type.File)
                        )
                );
            }
        } catch (Exception e) {

        }

        return result;

    }

    private FileTime getCreationDate(String absolutePath) {

        Path file = Paths.get(absolutePath);
        try {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            return attr.creationTime();
        } catch (IOException e) {
            return FileTime.fromMillis(0);
        }

    }

    public List<FileObject> getContents()
    {
        return contents;
    }

    public String getPath() {
        return path;
    }
}
