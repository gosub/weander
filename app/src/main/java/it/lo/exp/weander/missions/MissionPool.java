package it.lo.exp.weander.missions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MissionPool {

    private static final List<Mission> ALL = new ArrayList<>();
    private static final Random RANDOM = new Random();

    static {
        // ---- PHOTO ----
        add(MissionCategory.PHOTO, "Photograph the most interesting texture you can find here.");
        add(MissionCategory.PHOTO, "Take a photo that tells a story without any people in it.");
        add(MissionCategory.PHOTO, "Find and photograph something that doesn\u2019t belong here.");
        add(MissionCategory.PHOTO, "Capture the light \u2014 find the most beautiful shadow or reflection.");
        add(MissionCategory.PHOTO, "Photograph this place as if it were the cover of an album.");
        add(MissionCategory.PHOTO, "Find two things that rhyme visually and photograph them together.");
        add(MissionCategory.PHOTO, "Take a photo that would confuse a time traveler from 1800.");
        add(MissionCategory.PHOTO, "Find the smallest interesting detail here and make it fill the frame.");
        add(MissionCategory.PHOTO, "Get as low as you can and take a photo from that angle.");
        add(MissionCategory.PHOTO, "Photograph something you\u2019d walk past every day without noticing.");
        add(MissionCategory.PHOTO, "Frame a photo using something in the environment \u2014 a doorway, arch, or branches.");
        add(MissionCategory.PHOTO, "Find something beautiful in the mundane \u2014 a drain, a crack, peeling paint.");
        // new hand-crafted PHOTO
        add(MissionCategory.PHOTO, "Take a photo that only works at this exact time of day \u2014 and wouldn\u2019t make sense an hour from now.");
        add(MissionCategory.PHOTO, "Find a natural frame \u2014 doorway, arch, branches, gap in a fence \u2014 and photograph what\u2019s inside it. Then step through and photograph looking back.");
        add(MissionCategory.PHOTO, "Take five photographs that together tell a single wordless story of this place.");

        // ---- WRITING ----
        add(MissionCategory.WRITING, "Write a haiku inspired by the first thing you notice here.");
        add(MissionCategory.WRITING, "Describe this place in exactly six words. No more, no less.");
        add(MissionCategory.WRITING, "Invent a tiny fictional backstory for a stranger you see \u2014 don\u2019t approach them.");
        add(MissionCategory.WRITING, "Name this place as if it were a location in a fantasy novel. Explain the name.");
        add(MissionCategory.WRITING, "Write a one-paragraph short story that begins and ends here.");
        add(MissionCategory.WRITING, "What would a travel guide from the year 3000 say about this spot?");
        add(MissionCategory.WRITING, "List five sounds you hear. Then write one sentence about what they\u2019re whispering.");
        add(MissionCategory.WRITING, "Write a classified ad for this location. What is it selling?");
        add(MissionCategory.WRITING, "If this place had a mood, describe it without using any emotion words.");
        add(MissionCategory.WRITING, "Write a postcard to someone you miss, about this specific moment in this specific place.");
        add(MissionCategory.WRITING, "Invent the name of a person who lived here 100 years ago. What did they do?");
        add(MissionCategory.WRITING, "Write three rules for an imaginary secret society that meets at this exact spot.");
        // new hand-crafted WRITING
        add(MissionCategory.WRITING, "Listen without moving for 60 seconds. Write every distinct sound. Then write one sentence that somehow uses all of them.");
        add(MissionCategory.WRITING, "Write the resignation letter of someone who worked in the nearest building that looks like it has a history. What were they leaving?");
        add(MissionCategory.WRITING, "Choose one object you can see and write its autobiography in three sentences.");

        // ---- SOUND ----
        add(MissionCategory.SOUND, "Record the ambient sound for 30 seconds. Give it a title like it\u2019s a music track.");
        add(MissionCategory.SOUND, "Find the most surprising sound in this area and record it.");
        add(MissionCategory.SOUND, "Record a 15-second audio postcard \u2014 as if you\u2019re sending it to someone far away.");
        add(MissionCategory.SOUND, "Find the quietest spot nearby and record 20 seconds of near-silence.");
        add(MissionCategory.SOUND, "Find something that makes a sound when you interact with it. Record it.");
        add(MissionCategory.SOUND, "Record the sound of water, wind, or leaves. Name the human emotion it matches.");
        add(MissionCategory.SOUND, "Stand completely still for 60 seconds. Record the final 15 seconds.");
        add(MissionCategory.SOUND, "Find a percussive sound nearby \u2014 a tap, knock, scrape \u2014 and record a short rhythm.");
        add(MissionCategory.SOUND, "Record the single sound that best represents this neighborhood right now.");
        add(MissionCategory.SOUND, "Close your eyes and record 30 seconds. Write down what you heard that you hadn\u2019t noticed.");
        add(MissionCategory.SOUND, "Record yourself humming something this place makes you feel.");
        add(MissionCategory.SOUND, "Find two contrasting sounds \u2014 soft and harsh, near and far \u2014 and record both back to back.");
        // new hand-crafted SOUND
        add(MissionCategory.SOUND, "Find a spot where sounds from two different directions overlap. Record 30 seconds of that overlap point.");
        add(MissionCategory.SOUND, "Record a sound, then describe it in writing using only things it reminds you of \u2014 no direct description of what made it.");
        add(MissionCategory.SOUND, "Find the spot on this street that sounds most different from your front door. Record 20 seconds.");

        // ---- SOCIAL ----
        add(MissionCategory.SOCIAL, "Ask a stranger: what\u2019s the best-kept secret of this neighbourhood?");
        add(MissionCategory.SOCIAL, "Ask someone: if this street had a theme song, what would it be?");
        add(MissionCategory.SOCIAL, "Find a local shop and ask the person working there what they love most about it.");
        add(MissionCategory.SOCIAL, "Ask a stranger to describe this place in one word. Note it down.");
        add(MissionCategory.SOCIAL, "Ask someone who looks like they\u2019ve been here a while: what has changed most about this place?");
        add(MissionCategory.SOCIAL, "Ask a stranger: what would you change about this street if you could?");
        add(MissionCategory.SOCIAL, "Find someone who works nearby and ask what they usually eat for lunch.");
        add(MissionCategory.SOCIAL, "Ask a stranger for a local recommendation \u2014 somewhere tourists don\u2019t go.");
        add(MissionCategory.SOCIAL, "Ask someone if they know any history about this area. Write down what they say.");
        add(MissionCategory.SOCIAL, "Ask someone: if this neighbourhood were a person, how would you describe them?");
        add(MissionCategory.SOCIAL, "Find a shop owner and ask what their favourite thing in the shop is, and why.");
        add(MissionCategory.SOCIAL, "Ask a stranger: what\u2019s the one thing about this place that always cheers you up?");
        // new hand-crafted SOCIAL
        add(MissionCategory.SOCIAL, "Ask someone nearby what they\u2019re about to do next. Write it down \u2014 don\u2019t ask why.");
        add(MissionCategory.SOCIAL, "Find the business here that seems most like it shouldn\u2019t still exist. Go in. Buy something small, or just introduce yourself.");
        add(MissionCategory.SOCIAL, "Ask a stranger: what\u2019s one thing about this street they\u2019d change, and one thing they\u2019d keep forever?");

        // ---- CREATIVE ----
        add(MissionCategory.CREATIVE, "Sketch something you see here in under two minutes. Photograph the sketch.");
        add(MissionCategory.CREATIVE, "Rearrange small found objects \u2014 leaves, stones, bottle caps \u2014 into a tiny art piece. Photograph it.");
        add(MissionCategory.CREATIVE, "Find a spot where you\u2019d place a bench if you could. Photograph the view from where you\u2019d sit.");
        add(MissionCategory.CREATIVE, "If you could rename this street, what would you call it? Write the new name on paper and photograph it in place.");
        add(MissionCategory.CREATIVE, "Collect five natural objects. Arrange them by some quality only you can see. Photograph the result.");
        add(MissionCategory.CREATIVE, "Use your own shadow as part of a composition. Photograph it.");
        add(MissionCategory.CREATIVE, "Find a surface with an interesting pattern and make a rubbing on paper. Photograph both.");
        add(MissionCategory.CREATIVE, "Build the tallest stack of pebbles or similar objects you can manage. Photograph it.");
        add(MissionCategory.CREATIVE, "Imagine this spot as the opening scene of a film. Write the first line of the screenplay.");
        add(MissionCategory.CREATIVE, "Draw a rough map of this immediate area from memory, then photograph the map next to the real thing.");
        add(MissionCategory.CREATIVE, "Leave something here that a stranger might find \u2014 a small drawing, a folded note, a stone arrangement.");
        add(MissionCategory.CREATIVE, "Find the most photogenic angle of the most boring object here. Make it look like art.");
        // new hand-crafted CREATIVE
        add(MissionCategory.CREATIVE, "Collect exactly nine small objects you find here. Arrange them in order of the emotion they give you. Photograph the sequence.");
        add(MissionCategory.CREATIVE, "Use your body as a measuring tool \u2014 paces, arm spans, hand widths \u2014 to measure something large. Write the result as a poem of quantities.");
        add(MissionCategory.CREATIVE, "Find a completely unremarkable surface. Compose a photograph of it that makes a stranger stop scrolling.");

        // ---- OBSERVATION ----
        add(MissionCategory.OBSERVATION, "Count every distinct colour you can see from one spot. List them all.");
        add(MissionCategory.OBSERVATION, "Find the oldest-looking thing here. Guess its age. Photograph it.");
        add(MissionCategory.OBSERVATION, "What detail here would most people walk past without noticing? Document it.");
        add(MissionCategory.OBSERVATION, "Stand completely still for 60 seconds. Write down everything that changes around you.");
        add(MissionCategory.OBSERVATION, "Find three things that share the same colour but look completely different from each other.");
        add(MissionCategory.OBSERVATION, "Find evidence that something here is slowly disappearing \u2014 fading paint, crumbling concrete, a dying plant.");
        add(MissionCategory.OBSERVATION, "Find evidence that someone has been here before you \u2014 footprints, graffiti, a worn path.");
        add(MissionCategory.OBSERVATION, "Look up. Photograph what you see that you\u2019d miss looking straight ahead.");
        add(MissionCategory.OBSERVATION, "Find something that has clearly been repaired. How was it fixed? Document it.");
        add(MissionCategory.OBSERVATION, "Choose one square metre of ground and count everything alive in it.");
        add(MissionCategory.OBSERVATION, "Find something casting an interesting shadow right now. Will it look different in an hour?");
        add(MissionCategory.OBSERVATION, "Notice what\u2019s missing from this place that you\u2019d expect to find here. Write about it.");
        // new hand-crafted OBSERVATION
        add(MissionCategory.OBSERVATION, "Count three complete cycles of something that repeats around you \u2014 people passing, a signal changing, a sound returning. Write the intervals.");
        add(MissionCategory.OBSERVATION, "Identify the most temporary thing here and the most permanent thing here. Photograph both.");
        add(MissionCategory.OBSERVATION, "Find evidence of a decision someone made here \u2014 a worn path, a painted line, a worn-away corner. What were they deciding?");

        // ---- MULTI-STEP MISSIONS ----
        add(MissionCategory.PHOTO,
            "Find something broken or incomplete here.\n\nPhotograph it as it is.\n\nNow photograph it from an angle that makes it look intentional \u2014 as if the damage were the point.");
        add(MissionCategory.WRITING,
            "Choose a stranger you can observe without approaching.\n\nWrite their itinerary for today as you imagine it \u2014 not what they look like, just what they\u2019ve done and will do.\n\nSign it with a name you invent for them.");
        add(MissionCategory.CREATIVE,
            "Find the most boring object in sight.\n\nWrite a three-sentence advertisement for it \u2014 make it sound unmissable.\n\nPhotograph the object next to your ad.");
        add(MissionCategory.OBSERVATION,
            "Find the two things here that are furthest apart in age.\n\nEstimate how old each is.\n\nWrite one sentence connecting them.");
        add(MissionCategory.SOUND,
            "Stand still for 90 seconds with your eyes closed.\n\nRecord the last 20 seconds.\n\nWrite down what you heard that you hadn\u2019t noticed before you started.");
        add(MissionCategory.SOCIAL,
            "Find someone who looks like they come here regularly.\n\nAsk them one question about this place \u2014 anything you\u2019re genuinely curious about.\n\nWrite down their answer word for word.");
    }

    // ---- Composable mission generator ----

    private static final String[] OBS_QUALITIES = {
        "most weathered", "most out of place", "most recently changed",
        "oldest", "most repaired", "most ignored",
    };
    private static final String[] OBS_CONSTRAINTS = {
        "from the lowest angle you can manage",
        "as if it were important evidence",
        "in a way that makes it look significant",
        "so that a stranger would want to know more",
        "as if you were documenting it for a museum",
    };

    private static final String[] PHOTO_SUBJECTS = {
        "the thing that looks most out of time",
        "a surface no one would ever choose to photograph",
        "the boundary between two completely different textures",
        "something that casts a shadow more interesting than itself",
        "a detail at exactly knee height",
    };
    private static final String[] PHOTO_APPROACHES = {
        "Fill the frame completely \u2014 leave no background.",
        "Shoot into the light.",
        "Use something in the foreground to half-obscure it.",
        "Take it from directly above.",
        "Wait until a person or vehicle passes through the frame.",
    };

    private static final String[] WRITING_SUBJECTS = {
        "the sound that is most constant here",
        "the oldest surface you can see",
        "the gap between two buildings",
        "the thing nobody here would ever describe",
        "the point where two different streets meet",
    };
    private static final String[] WRITING_FORMS = {
        "Write one sentence that contains a lie and a truth in equal measure.",
        "Describe it as if to someone who has never left a single room.",
        "Write four words that together become a title for it.",
        "Write the first line of the letter it would send if it could.",
        "Describe it using only measurements and temperatures.",
    };

    private static Mission composedMission() {
        int pick = RANDOM.nextInt(3);
        if (pick == 0) {
            String quality    = OBS_QUALITIES[RANDOM.nextInt(OBS_QUALITIES.length)];
            String constraint = OBS_CONSTRAINTS[RANDOM.nextInt(OBS_CONSTRAINTS.length)];
            return new Mission(MissionCategory.OBSERVATION,
                "Find the " + quality + " thing here. Photograph it " + constraint + ".");
        } else if (pick == 1) {
            String subject  = PHOTO_SUBJECTS[RANDOM.nextInt(PHOTO_SUBJECTS.length)];
            String approach = PHOTO_APPROACHES[RANDOM.nextInt(PHOTO_APPROACHES.length)];
            return new Mission(MissionCategory.PHOTO,
                "Find " + subject + ". " + approach);
        } else {
            String subject = WRITING_SUBJECTS[RANDOM.nextInt(WRITING_SUBJECTS.length)];
            String form    = WRITING_FORMS[RANDOM.nextInt(WRITING_FORMS.length)];
            return new Mission(MissionCategory.WRITING,
                "Focus on " + subject + ". " + form);
        }
    }

    // ---- Public API ----

    private static void add(MissionCategory cat, String text) {
        ALL.add(new Mission(cat, text));
    }

    public static Mission random() {
        if (RANDOM.nextFloat() < 0.2f) return composedMission();
        return ALL.get(RANDOM.nextInt(ALL.size()));
    }

    public static Mission randomInCategory(MissionCategory category) {
        List<Mission> filtered = new ArrayList<>();
        for (Mission m : ALL) {
            if (m.getCategory() == category) filtered.add(m);
        }
        if (filtered.isEmpty()) return random();
        return filtered.get(RANDOM.nextInt(filtered.size()));
    }

    public static int totalCount() {
        return ALL.size();
    }
}
