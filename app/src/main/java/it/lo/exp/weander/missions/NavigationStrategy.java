package it.lo.exp.weander.missions;

public class NavigationStrategy {
    private final String name;
    private final String instruction;

    public NavigationStrategy(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    public String getName() { return name; }
    public String getInstruction() { return instruction; }
}
