package it.lo.exp.weander.missions;

import java.util.Random;

public class NavigationStrategyPool {

    private static final Random RNG = new Random();

    private static final String[] STARTS = {
        "Leave your front door.",
        "Walk to the nearest corner. Face the direction you'd normally avoid.",
        "Find the nearest park or green space and begin from its entrance.",
        "Begin at the first lamppost you can see from your door.",
        "Walk to the closest water — fountain, canal, or puddle — and start from its edge.",
        "Find the steepest street visible and begin at its foot.",
    };

    private static final String[] RULES = {
        "At each junction, take the street with the most trees.",
        "At each junction, choose the narrower option.",
        "At each junction, turn toward any downhill slope.",
        "At each junction, alternate: left, right, left, right.",
        "At each junction, go straight unless blocked; if blocked, turn left.",
        "Follow house numbers upward — always move toward higher numbers.",
        "At each junction, take the street that faces the sun.",
        "Always walk uphill if a slope is available.",
        "Take the first street where you can hear something new.",
        "Hug any wall or fence — stay as close as possible to a continuous barrier.",
        "Count the front doors: at each junction, take the street with more doors visible.",
        "At each junction, take the road you've never walked before.",
    };

    private static final String[] STOPS = {
        "Stop after %d turnings. Where you land is your destination.",
        "Stop after %d minutes of walking.",
        "Stop when you cross a bridge or pass under one.",
        "Stop when you reach a dead end — that street is your destination.",
        "Stop when you reach an open space: a square, a car park, a park.",
        "Stop at the first building whose purpose you can't immediately identify.",
        "Stop when you find a bench you've never sat on. Sit on it.",
        "Stop when you spot something you've never noticed before.",
        "Stop at the first street whose name you don't recognise.",
        "Stop when you've walked exactly %d blocks from your start.",
    };

    private static final String[] MODIFIERS = {
        "Do not use any street whose name you already know.",
        "Never retrace your steps.",
        "If you hesitate at any junction, take the option you'd normally skip.",
        "If you hear music at any point, follow it instead.",
        "Do not look at your phone from the moment you step out.",
        "Walk no faster than a slow stroll throughout.",
        "Notice what's above eye level — look up at every junction.",
        "Keep a mental note of the three most interesting things you pass.",
    };

    private static final String[] NAMES = {
        "The Drifter", "Wall Follower", "Sunseeker", "The Downhill Walk",
        "Narrow Path", "Odd Numbers", "The Listener", "Tree Line",
        "Dead Reckoning", "Crossroads Game", "The Gradient", "Door Counter",
    };

    public static NavigationStrategy random() {
        String start = STARTS[RNG.nextInt(STARTS.length)];
        String rule  = RULES[RNG.nextInt(RULES.length)];
        String name  = NAMES[RNG.nextInt(NAMES.length)];

        String rawStop = STOPS[RNG.nextInt(STOPS.length)];
        String stop;
        if (rawStop.contains("%d")) {
            // first placeholder: turnings (4–9), minutes (8–20), or blocks (3–7)
            if (rawStop.startsWith("Stop after %d turnings")) {
                stop = String.format(rawStop, 4 + RNG.nextInt(6));
            } else if (rawStop.startsWith("Stop after %d minutes")) {
                stop = String.format(rawStop, 8 + RNG.nextInt(13));
            } else {
                stop = String.format(rawStop, 3 + RNG.nextInt(5));
            }
        } else {
            stop = rawStop;
        }

        String modifier = RNG.nextFloat() < 0.4f
                ? " " + MODIFIERS[RNG.nextInt(MODIFIERS.length)]
                : "";

        String instruction = start + " " + rule + " " + stop + modifier;
        return new NavigationStrategy(name, instruction);
    }
}
