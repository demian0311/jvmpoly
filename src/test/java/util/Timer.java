package util;

public class Timer {

    private String name;
    private Long startTime;
    private Long duration;

    public Timer(String nameIn){
        name = nameIn;
        startTime = System.currentTimeMillis();
    }

    public Long stop(){
        duration = System.currentTimeMillis() - startTime;
        return duration;
    }

    public String toString(){
        this.stop();
        return "Timer " + name + " took " + duration + "ms";
    }
}
