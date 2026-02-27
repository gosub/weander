package it.lo.exp.weander.missions;

public enum MissionCategory {
    PHOTO("\uD83D\uDCF8", "Photo"),
    WRITING("\u270D\uFE0F", "Writing"),
    SOUND("\uD83C\uDFA7", "Sound"),
    SOCIAL("\uD83D\uDCAC", "Social"),
    CREATIVE("\uD83C\uDFA8", "Creative"),
    OBSERVATION("\uD83D\uDC41\uFE0F", "Observation");

    private final String emoji;
    private final String displayName;

    MissionCategory(String emoji, String displayName) {
        this.emoji = emoji;
        this.displayName = displayName;
    }

    public String getEmoji() { return emoji; }
    public String getDisplayName() { return displayName; }
}
