package models;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static final AtomicInteger nextId = new AtomicInteger(1);

    private int id;
    private String name;
    private AtomicBoolean onRide;

    public User(String name) {
        this.id = nextId.getAndIncrement();
        this.name = name;
        this.onRide = new AtomicBoolean(false);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnRide() {
        return onRide.get();
    }

    public void startRide() {
        this.onRide.compareAndExchange(false,true);
    }

    public void completeRide(){
        this.onRide.compareAndExchange(true,false);
    }
}
