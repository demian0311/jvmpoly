package com.neidetcher.rosetta.level1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by demian on 10/7/14.
 */
public class Level1Test {
    @Test public void helloWorld(){
        List<Level1> level1s = Arrays.asList(new Level1Java(), new Level1Scala());

        for(Level1 currLevel: level1s){
            String result = currLevel.helloWorld("Demian");
            Assert.assertEquals("hello Demian", result);
        }
    }

    @Test public void loopName(){
        List<Level1> level1s = Arrays.asList(new Level1Java(), new Level1Scala());

        for(Level1 currLevel: level1s){
            String result = currLevel.loopName("Demian", 3);
            Assert.assertEquals("DemianDemianDemian", result);
        }
    }

}
