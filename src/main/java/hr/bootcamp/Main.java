package hr.bootcamp;

import hr.bootcamp.exception.NameInWrongFormatException;
import hr.bootcamp.exception.UserNotFoundException;
import hr.bootcamp.exception.WorkingHoursOver40Exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Worker> workers = new ArrayList<>();
        List<Manager> managers = new ArrayList<>();

        System.out.println("Hello user!");

        while(true){

            System.out.println("Please enter command:");
            String command = scanner.nextLine();

            //Employee entry
            if(command.equalsIgnoreCase("add m") || command.equalsIgnoreCase("add w")){
                System.out.println("Employee entry started.");
                while(true){

                    System.out.println("Please enter employee name: ");

                    String name = null;
                    boolean nameInCorrectFormat = false;

                    do{
                        try{
                            name = scanner.nextLine();
                            nameInCorrectFormat = checkName(name);
                        } catch (NameInWrongFormatException e){
                            System.out.println("Name cannot contain numbers.");
                        }
                    }while(!nameInCorrectFormat);

                    if(name.equalsIgnoreCase("end")){
                        System.out.println("Employee entry ended.");
                        break;
                    }

                    System.out.println("Please enter employee working hours: ");
                    boolean workingHoursInCorrectFormat = false;
                    Long workingHours = null;
                    do{
                        try{
                            workingHours = scanner.nextLong();
                            workingHoursInCorrectFormat = checkHours(workingHours);
                        }catch (WorkingHoursOver40Exception | InputMismatchException e){
                            System.out.println("Employee hours cannot be over 40 or there has been an input mismatch.");
                            System.out.println("Please try again: ");

                        }
                        scanner.nextLine();
                    }while(!workingHoursInCorrectFormat);



                    if(command.equals("add w")){
                        workers.add(new Worker(name, workingHours));
                    }else {
                        managers.add(new Manager(name, workingHours));
                    }
                }
            }else if(command.equalsIgnoreCase("show")){
                show(managers, workers);
            }else if(command.startsWith("remove")){
                String[] split = command.split("\\s+");
                int id = Integer.parseInt(split[1]);
                try{
                    deleteEmployee(managers, workers, id);
                }catch (UserNotFoundException e){
                    System.out.println("User with id " + id + " doesn't exist, pelase add employee first.");
                }

            }else if(command.startsWith("search")){
                String[] split = command.split("\\s+");
                String name = split[1];
                try{
                    searchEmployee(managers, workers, name);
                }catch (UserNotFoundException e){
                    System.out.println(name + " doesn't exist, please add employee first.");
                }
            }else if(command.equalsIgnoreCase("exit")){
                break;
            }else {
                System.out.println("Command not recognized. Try again.");
            }
        }
    }

    private static boolean checkName(String name) throws NameInWrongFormatException {
        if(name.matches(".*\\d.*")) throw new NameInWrongFormatException();
        return true;
    }

    private static void searchEmployee(List<Manager> managers, List<Worker> workers, String name) throws UserNotFoundException {

        boolean userFound = false;

        for(Manager m : managers){
            if(m.getName().equals(name)){
                System.out.println(name + " worked " + m.getWorkingHours() + " hours this week.");
                userFound = true;
            }
        }
        for(Worker w : workers){
            if(w.getName().equals(name)){
                System.out.println(name + " worked " + w.getWorkingHours() + " hours this week.");
                userFound = true;
            }
        }
        if(userFound == false){
            throw new UserNotFoundException();
        }
    }

    private static void deleteEmployee(List<Manager> managers, List<Worker> workers, int id) throws UserNotFoundException {

        Manager managerToRemove = null;
        Worker workerToRemove = null;

        for(Manager m : managers){
            if(m.getId() == id){
                managerToRemove = m;
            }
        }
        if(managerToRemove != null){
            managers.remove(managerToRemove);
            System.out.println(managerToRemove.getName() + " has been removed successfully.");
        }

        for(Worker w : workers){
            if(w.getId() == id){
                workerToRemove = w;
            }
        }
        if(workerToRemove != null){
            workers.remove(workerToRemove);
            System.out.println(workerToRemove.getName() + " has been removed successfully.");
        }
        if(workerToRemove == null && managerToRemove == null) throw new UserNotFoundException();
    }

    private static void show(List<Manager> managers, List<Worker> workers) {
        System.out.println("ID\tName\tHours_worked\tSalary");

        List<Employee> people = new ArrayList<>();

        for(Manager m : managers){
            people.add(m);
        }
        for(Worker w : workers){
            people.add(w);
        }

        Collections.sort(people);
        for(Employee e : people){
            System.out.printf("%s\t%s\t%s\t\t\t\t%s%n", e.getId(), e.getName(), e.getWorkingHours(), e.calculateSalary());
        }
    }

    private static boolean checkHours(Long workingHours) throws WorkingHoursOver40Exception {
        if(workingHours > 40){
            throw new WorkingHoursOver40Exception();
        }
        return true;
    }
}
