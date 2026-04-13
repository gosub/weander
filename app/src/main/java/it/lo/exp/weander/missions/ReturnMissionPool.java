package it.lo.exp.weander.missions;

import java.util.Random;

public class ReturnMissionPool {

    private static final Random RNG = new Random();

    private static final Mission[] MISSIONS = {
        new Mission(MissionCategory.OBSERVATION,
            "You have been near here before. Stand still for one minute. What has changed since you were last here?"),
        new Mission(MissionCategory.OBSERVATION,
            "Find something you definitely didn\u2019t notice the last time you were in this area. Document it."),
        new Mission(MissionCategory.PHOTO,
            "Photograph this place as if you were seeing it for the first time. What would you have noticed then that you walk past now?"),
        new Mission(MissionCategory.WRITING,
            "You\u2019ve been somewhere near here before. Write a paragraph about what is exactly the same, and one about what is different."),
        new Mission(MissionCategory.WRITING,
            "Write a letter to your past self from the last time you were near here. What would you tell them about this place?"),
        new Mission(MissionCategory.OBSERVATION,
            "Find the thing in this area that has changed the most since a previous visit \u2014 even if you can only guess. Photograph it."),
        new Mission(MissionCategory.PHOTO,
            "Find the angle or vantage point you have never photographed this place from. Use it."),
        new Mission(MissionCategory.CREATIVE,
            "You know this area a little. Make a map of it from memory \u2014 mark what you remember and what you\u2019re not sure of. Then photograph your map against the real thing."),
        new Mission(MissionCategory.OBSERVATION,
            "Look for evidence that time has passed since you were last near here \u2014 something grown, faded, built, or removed."),
        new Mission(MissionCategory.WRITING,
            "Describe this place in two short paragraphs: one as a stranger seeing it for the first time, one as someone who knows it. Which version feels true?"),
        new Mission(MissionCategory.SOCIAL,
            "You\u2019ve been near here before. Ask someone nearby what they think has changed about this area recently. Compare it to your own memory."),
        new Mission(MissionCategory.CREATIVE,
            "Leave something here that marks your return \u2014 a small arrangement, a note tucked somewhere subtle, a drawing. Something only you would know to look for."),
    };

    public static Mission random() {
        return MISSIONS[RNG.nextInt(MISSIONS.length)];
    }
}
