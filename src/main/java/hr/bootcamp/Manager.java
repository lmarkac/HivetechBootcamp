package hr.bootcamp;

public class Manager extends Employee{

    private int salaryPerHour = 20;

    public Manager(String name, Long workingHours){
        super(name, workingHours);
    }

    public Long getId(){
        return super.getId();
    }

    public String getName(){
        return super.getName();
    }

    public Long getWorkingHours(){
        return super.getWorkingHours();
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
