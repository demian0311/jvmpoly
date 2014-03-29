package jtrade.otherfeatures.interfaces;

import org.junit.Test;
import static org.junit.Assert.*;

interface Left{
    default public String open() {
        return "Left hand opened";
    }
}

interface Right{
    default public String open() {
        return "Right hand opened";
    }
}

abstract class Eyes{
    public String open() {
        return "Eyes opened";
    }
}

public class InterfaceMethods {

    class Body extends Eyes{
    }

    @Test public void extendingAbstractClass(){
        Body body = new Body();
        assertEquals("Eyes opened", body.open());
    }

    class LeftArm implements Left{
    }

    @Test public void implementInterfaceWithDefault(){
        LeftArm leftArm = new LeftArm();
        assertEquals("Left hand opened", leftArm.open());
    }

    interface HandsInterface extends Left, Right{
        /** You have to disambiguate. */
        default String open(){
            return Right.super.open();
        }
    }

    class HandsInterfaceClass implements HandsInterface{
    }

    @Test public void handsInterface(){
        HandsInterfaceClass hif = new HandsInterfaceClass();
        assertEquals("Right hand opened", hif.open());
    }

    class Hands implements Left, Right{
        /** You have to disambiguate. */
        @Override public String open() {
            return "Hands opened";
        }
    }

    @Test public void hands(){
        Hands hands = new Hands();
        assertEquals("Hands opened", hands.open());
    }

    class Torso extends Eyes implements Left{
    }

    @Test public void extendingAbstractAndImplementInterface(){
        Torso torso = new Torso();
        assertEquals("Eyes opened", torso.open());
    }
}
