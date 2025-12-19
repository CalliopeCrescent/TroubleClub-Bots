package org.calliope.tcdiscordbots.resources;

import java.awt.*;
import java.util.Random;

public class RandomColor {

    public static Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
