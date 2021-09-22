package me.ulearn;

import java.nio.file.attribute.FileTime;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class FileObject {

    private String name;
    private long size;
    private String humanSize;
    private String timeCreated;
    private Type type;

    public FileObject(String name, long size, String timeCreated, Type type) {
        this.name = name;
        this.size = size;
        this.humanSize = humanReadableByteCountBin(size);
        this.timeCreated = timeCreated;
        this.type = type;
    }

    public static String humanReadableByteCountBin(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getSize() {
        return humanSize;
    }

    public String getCreationDate() {
        return timeCreated.toString();
    }

    public enum Type {
        File,
        Directory,
    }
}
