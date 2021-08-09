package hr.bootcamp;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Employee implements Comparable<Employee>{
    private static final AtomicInteger count = new AtomicInteger(0);
    protected Long id;
    protected String name;
    protected Long workingHours;

    protected Employee(String name, Long workingHours){
        this.name = name;
        this.workingHours = workingHours;
        this.id = (long) count.incrementAndGet();
    }

    protected Long getId(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected Long getWorkingHours(){
        return this.workingHours;
    }

    protected abstract Long calculateSalary();
}
