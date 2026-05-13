package models;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Driver {
    private static final AtomicInteger nextId = new AtomicInteger(1);

    private int id;
    private String name;
    private String vehicleNum;
    private AtomicBoolean available;

    public Driver(String name, String vehicleNum) {
        this.id = nextId.getAndIncrement();
        this.name = name;
        this.vehicleNum = vehicleNum;
        this.available = new AtomicBoolean(true);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public Boolean getAvailable() {
        return available.get();
    }

    public boolean markAvailable() {
        return this.available.compareAndSet(false,true);
    }

    public boolean tryAndReserve() {
        return this.available.compareAndSet(true,false);
    }
}
