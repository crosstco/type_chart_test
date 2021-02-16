// Enum for gen 2-5 pokemon types

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

    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
