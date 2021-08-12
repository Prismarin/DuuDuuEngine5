package dev.kelvin.gameengine.utils;

public class Timer {

    private long now, lastTime, cooldown;
    private TimerTask task;

    public boolean oneShot;

    private boolean running;

    public Timer(long time, TimerTask task) {
        this.cooldown = time;
        now = this.cooldown;
        this.task = task;
    }

    public void start() {
        running = true;
        now = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick() {
        if (running) {
            now += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            if (now > cooldown) {
                task.onTime();
                if (!oneShot)
                    now = 0;
            }
        }
    }

    public void stop() {
        running = false;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public void trigger() {
        task.onTime();
    }

    public void triggerReset() {
        task.onTime();
        now = 0;
    }

    public void setTask(TimerTask task) {
        this.task = task;
    }

}
