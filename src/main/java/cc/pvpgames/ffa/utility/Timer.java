package cc.pvpgames.ffa.utility;


import lombok.Getter;

@Getter
public class Timer {

    private long start;
    private long end;

    public Timer(int length) {
        this.start = System.currentTimeMillis();
        this.end = this.start + (length * 1000);
    }

    public long getTimeLeft() {
        return this.end - System.currentTimeMillis();
    }

    public boolean active() {
        return System.currentTimeMillis() < this.end;
    }

}
