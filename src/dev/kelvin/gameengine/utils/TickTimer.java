package dev.kelvin.gameengine.utils;

public class TickTimer {

    private int now, cooldown;
    private TimerTask task;

    public boolean oneShot;

    private boolean running;

    public TickTimer(int time, TimerTask task) {
        this.cooldown = time;
        now = this.cooldown;
        this.task = task;
    }

    public void start() {
        running = true;
        now = 0;
    }

    public void tick() {
        if (running) {
            now++;
            if (now >= cooldown) {
                task.onTime();
                if (!oneShot)
                    now = 0;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public int getNow() {
        return now;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setTask(TimerTask task) {
        this.task = task;
    }

    public void trigger() {
        task.onTime();
    }

    public void triggerReset() {
        task.onTime();
        now = 0;
    }

}
