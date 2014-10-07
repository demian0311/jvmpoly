package com.neidetcher.rosetta.level1;

public class Level1Java implements Level1 {
    @Override
    public String helloWorld(String name) {
        return "hello " + name;
    }

    @Override
    public String loopName(String name, Integer times) {
        StringBuffer outString = new StringBuffer();
        for (int i = 0; i < times; i++) {
            outString.append(name);
        }

        return outString.toString();
    }

}
