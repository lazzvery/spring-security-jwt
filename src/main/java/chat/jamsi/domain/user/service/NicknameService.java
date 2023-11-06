package chat.jamsi.domain.user.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class NicknameService {
    private static final String[] modifiers = {
            "Swift", "Gracious", "Vigorous", "Elegant", "Diligent",
            "Steadfast", "Bright", "Deliberate", "Joyful", "Careful",
            "Enthusiastic", "Gent", "Cautious", "Quiet", "Happy",
            "Patient", "Humble", "Fierce", "Vivid", "Playful",
            "Confident", "Effortless", "Prompt", "Graceful", "Courageous",
            "Radiant", "Honest", "Brisk", "Sincere", "Modest",
            "Decisive", "Soft", "Blissful", "Respectful", "Free",
            "Abundant", "Precise", "Fervent", "Zealous",
            "Ab", "Vivacious", "Loud", "Daring", "Steady",
            "Brave", "Carefree", "Wholehearted", "Abrupt", "Comical",
            "Meticulous", "Passionate", "Relentless", "Punctual", "Intent",
            "Honest", "Strident", "Unabashed", "Coherent", "Bold",
            "Tender", "Intense", "Ardent", "Gleeful", "Vigilant",
            "Affectionate", "Willing", "Proficient", "Hearty", "Sharp",
            "Subtle", "Fearless", "Profound"
    };

    private static final String[] animals = {
            "Lion", "Elephant", "Tiger", "Giraffe", "Kangaroo",
            "Dolphin", "Panda", "Monkey", "Zebra", "Cheetah",
            "Hippo", "Gorilla", "Koala", "Penguin", "Ostrich",
            "Bear", "Wolf", "Fox", "Lemur", "Sloth",
            "Otter", "Duck", "Eagle", "Shark", "Octopus",
            "Koala", "Owl", "Hedgehog", "Jaguar", "Seal",
            "Turtle", "Flamingo", "Gazelle", "Rhinoceros", "Chimpanzee",
            "Camel", "Giraffe", "Gibbon", "Peacock", "Hippopotamus",
            "Llama", "Alpaca", "Jaguar", "Armadillo", "Moose",
            "Panther", "Kangaroo", "Porcupine", "Bison", "Meerkat",
            "Squirrel", "Dolphin", "Platypus", "Coyote", "Dingo",
            "Ferret", "Pangolin", "Salamander", "Lynx", "Dalmatian",
            "Ocelot", "Quokka", "Okapi", "Vulture", "Wolverine",
            "Cassowary", "Wallaby", "Walrus", "Weasel", "Woodpecker",
            "Yak", "Vulture", "Narwhal", "Nightingale", "Nudibranch",
            "Numb-ray", "Horn-bill", "Horned Frog", "Zorse", "Xerus",
            "Du gong", "Dhole", "Red Panda", "Manatee", "Lemming"
    };

    private final Random random = new Random();

    public String getNickname() {
        int modifierIndex = random.nextInt(modifiers.length);
        int animalIndex = random.nextInt(animals.length);

        String modifier = modifiers[modifierIndex];
        String animal = animals[animalIndex];

        return modifier + " " + animal;
    }
}
