package it.lo.exp.weander.missions;

import java.util.Random;

public class ConstraintPool {

    private static final Random RNG = new Random();

    private static final String[] CONSTRAINTS = {
        "Do this without stopping moving.",
        "Complete it in under 4 minutes.",
        "Use only your non-dominant hand.",
        "Do not speak while doing it.",
        "You may only look at your subject for 10 seconds total.",
        "Do not retrace any step you take.",
        "Complete it without crouching or bending.",
        "You must hum something the entire time.",
        "Do not use your phone screen while completing it — only at the end to document.",
        "Stay within a 10-metre radius.",
        "Do it as slowly as possible.",
        "Narrate what you are doing under your breath.",
        "Close your eyes for 30 seconds first, then begin.",
        "You have exactly one attempt — no retakes, no editing.",
        "Do it while facing away from your destination.",
        "Complete it without touching any man-made surface.",
        "Do not look at the result until you have fully finished.",
        "Begin from the furthest point you can see.",
        "Every decision must be made in under three seconds.",
        "You may only move in straight lines — no curves.",
    };

    /** 35% chance of returning a constraint; otherwise returns null. */
    public static String maybeRandom() {
        if (RNG.nextFloat() >= 0.35f) return null;
        return CONSTRAINTS[RNG.nextInt(CONSTRAINTS.length)];
    }
}
