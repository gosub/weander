package it.lo.exp.weander.missions;

public class Mission {
    private final MissionCategory category;
    private final String text;

    public Mission(MissionCategory category, String text) {
        this.category = category;
        this.text = text;
    }

    public MissionCategory getCategory() { return category; }
    public String getText() { return text; }
    public String getCategoryLabel() { return category.getEmoji() + "  " + category.getDisplayName(); }
}
