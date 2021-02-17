// Enum for gen 2-5 pokemon types

import java.util.HashSet;

public enum Type {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL;

    private static HashSet<String> enumValues;

    public static boolean contains(String s) {
        if (enumValues == null) {
            enumValues = new HashSet<>();
            for (Type type : Type.values()) {
                enumValues.add(type.name());
            }
        }
        return enumValues.contains(s);
    }

    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
