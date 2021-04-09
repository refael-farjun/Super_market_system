package Employees.PresentationLayer;

import Employees.BuisnessLayer.EmployeeController;
import Employees.BuisnessLayer.FacadeController;
import Employees.BuisnessLayer.Shift;
import Employees.BuisnessLayer.WeeklyShifts;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static CLI cli = null;
    private CLIController cliController;
    private String userID;
    Scanner scanner;

    private CLI(){
        cliController = CLIController.getInstance();
        scanner = new Scanner(System.in);
    }

    public static CLI getInstance(){
        if (cli == null)
            cli = new CLI();
        return cli;
    }

    public void initData(){
        CLIController.initData();
    }

    private boolean isNameValid(String name){
        return name != null && !name.equals("") && !name.equals(" ");
    }

    private boolean isIdValid(String id){
        return id.length() == 9;
    }

//    private boolean isDateValid(LocalDate date){
//        LocalDate now = LocalDate.now();
//        return true;
//    }



    //starts the login menu of the program
    public void start() {

        String ID;
        int action;

        do {


            DisloginMenu();
            ID = scanner.next();
            cliController.setUserID(ID);
            if (cliController.checkAuthorizedHrOrGenral(ID).getValue()) {
                do {
                    //The User is Hr or General manager
                    DisMmainMenu();
                    action = scanner.nextInt();
                    if (action == 3)
                        break;
                    scanner.nextLine();
                    cliController.Mmainmanue(action);
                } while (true);
            } else {
                do { //TODO
                    //The User is Regular Employee (not Hr or General manager)
                    DisEmainMenu();
                    action = scanner.nextInt();
                    if (action == 5)
                        break;
                    scanner.nextLine();
                    cliController.EmainMenu(action);
                } while (true);

            }
        }while (true);
    }

    public void DisloginMenu(){
        System.out.println("""
                Welcome!
                Please Enter ID:
                """);
    }


    public void DisMmainMenu(){
        System.out.println("""
                1) Employees Menu
                2) Shifts Menu
                3) Exit""");
    }

    //------------------------------MANAGER ONLY----------------------------------

    public void MempMenu() {
        int action;
        do {
            DisMempMenu();
            action = scanner.nextInt();
            if (action == 3)
                break;
            scanner.nextLine();
            cliController.MempMenu(action);
        } while (true);

    }

    public void DisMempMenu(){
        System.out.println("""
                1) Add new employee to the system
                2) update or edit existing employee
                3) Exit""");
    }

    public void MempUpdateMenu() {
        int action;
        do {
            DisMempUpdateMenu();
            action = scanner.nextInt();
            if (action == 8)
                break;
            scanner.nextLine();
            cliController.MupdateEmployeeMenu(action);
        } while (true);

    }

    public void DisMempUpdateMenu(){
        System.out.println("""
                1) Update employee name
                2) Update employee bank account
                3) Update employee salary
                4) Update employee sick days
                5) Update employee study found
                6) Update employee days off
                7) Update employee role
                8) Exit""");
    }

    public void MshiftsMenu() {
        int action;
        do {
            DisMshiftsMenu();
            action = scanner.nextInt();
            if (action == 0)
                break;
            scanner.nextLine();
            cliController.MshiftsMenu(action);
        } while (true);

    }

    public void DisMshiftsMenu(){
        System.out.println("""
                1) Choose shift by date
                2) Choose shift from future shifts
                3) Generate 1 Week Shifts to future shifts
                4) Exit""");
    }

    public void MSingleShiftMenu(Shift shift) {
        int action;
        do {
            MSingleShiftDisplay(shift);
            action = scanner.nextInt();
            if (action == 0)
                break;
            scanner.nextLine();
            cliController.MSingleShiftOptions(action, shift);
        }while (true);
    }

    private void MSingleShiftDisplay(Shift shift) {
        System.out.println("""
                Shift Date: %s
                Start: %s\tEnd: %s
                
                """.formatted(shift.getDate(), shift.getStart(), shift.getEnd()));

        System.out.println("""
                1) Assign Employee
                2) Get Employees Preferences
                3) Close Shift
                4) Open Shift
                5) Get shift's status
                
                """);
    }

    public void weeksMenu() {
        int action;
        do {
            DisMWeeksShiftMenu();
            action = scanner.nextInt();
            if (action == 0)
                break;
            scanner.nextLine();
            cliController.MWeeksMenu(action);
        }while (true);
    }


    public void DisMWeeksShiftMenu(){
        System.out.println("""
                1) This week shifts 
                1) Next week shifts
                3) Exit""");
    }


    //----------------------------------------------------------------------------




    //------------------------------EMPLOYEE ONLY---------------------------------

    public void EmainMenu() {
        int action;
        do {
            DisEmainMenu();
            action = scanner.nextInt();
            if (action == 5)
                break;
            scanner.nextLine();
            cliController.EmainMenu(action);
        } while (true);

    }

    public void DisEmainMenu(){
        System.out.println("""
                1) Show all my information
                2) Show my preferences for a shift
                3) Apply preferences for a shift
                4) Show colleagues whom work with me in a shift
                5) Exit""");
    }

    //-----------------------------------------------------------------------------


    public void displayShift(Shift s) {
        System.out.println("""
                Date: %s
                Start: %s\tEnd: %s
                
                """.formatted(s.getDate(), s.getStart(), s.getEnd()));
    }

    public void displayWeekly(List<WeeklyShifts> weeks) {
        for(WeeklyShifts week: weeks)
            for(Shift s: week.getShifts())
                displayShift(s);
    }

    //display `msg` to the user and returns an int read from the user
    public int getInt(String msg) {
        System.out.println(msg);
        int out = scanner.nextInt();
        scanner.nextLine();
        return out;
    }

    //display `msg` to the user and returns a string read from the user
    public String getString(String msg) {
        System.out.println(msg);
        String out = scanner.nextLine();
//        scanner.nextLine();
        return out;
    }

    //display `msg` to the user and returns LocalDate read from the user
    public LocalDate getDate(String msg) {
        System.out.println(msg);
        System.out.println("Enter the day:");
        int day = scanner.nextInt();
        System.out.println("Enter the month:");
        int month = scanner.nextInt();
        System.out.println("Enter the year:");
        int year = scanner.nextInt();
        return LocalDate.of(year,month,day);
    }
    //display `msg` to the user
    public void print(String msg) {
        System.out.println(msg);
    }



}
