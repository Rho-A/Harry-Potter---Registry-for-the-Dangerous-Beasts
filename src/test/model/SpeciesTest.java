package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpeciesTest {
    private Species flobberworm;
    private Species ghoul;
    private Species kneazle;
    private Species griffin;
    private Species quintaped;

    @BeforeEach
    void setUp() {
        flobberworm = new Species("Flobberworm");
        ghoul = new Species("Ghoul");
        kneazle = new Species("Kneazle");
        griffin = new Species("Griffin");
        quintaped = new Species("Quintaped");

    }


}
