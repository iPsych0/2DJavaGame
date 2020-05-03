package dev.ipsych0.myrinnia.worlds;

import java.awt.*;
import java.io.Serializable;

public interface Weather extends Serializable {
    void tick();

    void render(Graphics2D g);
}
