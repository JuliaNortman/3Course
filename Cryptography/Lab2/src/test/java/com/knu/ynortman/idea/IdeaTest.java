package com.knu.ynortman.idea;

import org.junit.Test;
import static org.junit.Assert.*;

public class IdeaTest {

    private Key key;
    private Idea idea;

    private String string1 =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
                    "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                    "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
                    "qui officia deserunt mollit anim id est laborum";

    private String string2 = "In a free hour, when our power of choice is untrammelled and when nothing prevents " +
            "our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But " +
            "in certain circumstances and owing to the claims of duty or the obligations of business";

    private String string3 = "But I must explain to you how all this mistaken idea of denouncing pleasure and " +
            "praising pain was born and I will give you a complete account of the system, and expound the actual " +
            "teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, " +
            "dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how " +
            "to pursue pleasure rationally encounter consequences that are extremely painful.";

    private String string4 = "On the other hand, we denounce with righteous indignation and dislike men who are " +
            "so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they " +
            "cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail " +
            "in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. " +
            "These cases are perfectly simple and easy to distinguish.";

    private String string5 = "Posuere urna nec tincidunt praesent semper feugiat nibh sed. In nibh mauris cursus " +
            "mattis molestie a iaculis. Dui faucibus in ornare quam viverra orci. Volutpat ac tincidunt vitae semper " +
            "quis lectus nulla at. Mauris vitae ultricies leo integer. Enim lobortis scelerisque fermentum dui faucibus " +
            "in ornare. Ac ut consequat semper viverra nam libero.";

    private String string6 = "Nam libero justo laoreet sit amet cursus. Platea dictumst vestibulum rhoncus est " +
            "pellentesque elit ullamcorper dignissim cras. Nunc vel risus commodo viverra maecenas accumsan lacus " +
            "vel. Ultricies mi eget mauris pharetra. In nibh mauris cursus mattis. Imperdiet massa tincidunt nunc " +
            "pulvinar sapien et ligula.";

    private String string7 = "Sit amet facilisis magna etiam tempor. Convallis posuere morbi leo urna molestie " +
            "at elementum. Lectus vestibulum mattis ullamcorper velit sed. Rutrum tellus pellentesque eu tincidunt. " +
            "Lectus proin nibh nisl condimentum id venenatis. Fermentum dui faucibus in ornare quam viverra.";

    public IdeaTest() {
        key = new Key();
        key.setK(0, 0x050c);
        key.setK(1, 0x0a0b);
        key.setK(2, 0x00f0);
        key.setK(3, 0x0e00);
        key.setK(4, 0x0501);
        key.setK(5, 0x0103);
        key.setK(6, 0x010d);
        key.setK(7, 0x00cd);

        idea = new Idea(key);
    }

    @Test
    public void ideaTest() {
        assertEquals(string1.trim(), idea.decrypt(idea.encrypt(string1)).trim());
        assertEquals(string2.trim(), idea.decrypt(idea.encrypt(string2)).trim());
        assertEquals(string3.trim(), idea.decrypt(idea.encrypt(string3)).trim());
        assertEquals(string4.trim(), idea.decrypt(idea.encrypt(string4)).trim());
        assertEquals(string5.trim(), idea.decrypt(idea.encrypt(string5)).trim());
        assertEquals(string6.trim(), idea.decrypt(idea.encrypt(string6)).trim());
        assertEquals(string7.trim(), idea.decrypt(idea.encrypt(string7)).trim());
    }
}