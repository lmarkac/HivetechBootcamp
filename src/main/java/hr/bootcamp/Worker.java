package hr.bootcamp;

public class Worker extends Employee {

    private int salaryPerHour = 10;

    public Worker(String name, Long workingHours){
        super(name, workingHours);
    }

    @Override
    protected Long calculateSalary() {
        return salaryPerHour * getWorkingHours();
    }

    @Override
    public int compareTo(Employee e) {
        return this.getName().compareTo(e.getName());
    }
}
