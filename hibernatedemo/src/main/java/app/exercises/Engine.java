package app.exercises;

import app.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() {
       this.RemoveTowns();
    }

    /*
    2.Remove Objects
     */
    private void RemoveObjects()
    {


        List<Town> towns = this.entityManager.createQuery("FROM Town",Town.class).getResultList();

        for (Town town : towns) {
            if(town.getName().length()>5)
            {
                this.entityManager.detach(town);

            }
        }

        this.entityManager.getTransaction().begin();

        System.out.println();
        for (Town town : towns) {
            if(this.entityManager.contains(town))
            {
                town.setName(town.getName().toLowerCase());
                this.entityManager.merge(town);
            }
        }
        this.entityManager.getTransaction().commit();
    }

    /*
        3.Contains Employee
     */
    private void containsEmployee()
    {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        try {
            this.entityManager.getTransaction().begin();
            Employee employee = this.entityManager.createQuery("FROM Employee WHERE concat(first_name,' ',last_name) = :name", Employee.class)
                    .setParameter("name", name).getSingleResult();
            System.out.println("Yes");
        }catch (NoResultException nre)
        {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }

    /*
    4.Employees with Salary Over 50 000
     */
    private void EmployeesWithSalaryOver()
    {
        this.entityManager.getTransaction().begin();

        List<Employee> employee = this.entityManager.createQuery("FROM Employee WHERE salary>50000",Employee.class).getResultList();

        for (Employee emp : employee) {
            System.out.println(emp.getFirstName());
        }

        this.entityManager.getTransaction().commit();
    }

    /*
    5.Employees from Department
     */
    private void EmployeesFromDepartment()
    {
        this.entityManager.getTransaction().begin();

        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE department_id = 6 ORDER BY salary ASC,employee_id ASC",Employee.class).getResultList();

        for (Employee em : employees) {
            System.out.println(String.format("%s %s from %s - %.2f",em.getFirstName(),em.getLastName(),em.getDepartment().getName(),em.getSalary()));
        }

        this.entityManager.getTransaction().commit();
    }

    /*
    6.Adding a New Address and Updating Employee
     */
    private void AddingNewAddressAndUpdatingemployee()
    {
            Scanner scanner = new Scanner(System.in);
            String lastName = scanner.nextLine();

            this.entityManager.getTransaction().begin();

            Address address = new Address();
            address.setText("Vitoshka 15");

            Town town = this.entityManager.createQuery("FROM Town WHERE name = 'Sofia'",Town.class).getSingleResult();

            address.setTown(town);

            this.entityManager.persist(address);

            Employee employee = this.entityManager.createQuery("FROM Employee WHERE last_name = :name",Employee.class)
                    .setParameter("name",lastName)
                    .getSingleResult();

            this.entityManager.detach(employee.getAddress());
            employee.setAddress(address);
            this.entityManager.merge(employee);

            this.entityManager.getTransaction().commit();
    }

    /*
    7.Addresses with Employee Count
     */
    private void AddressesWithEmployeeCount()
    {
        this.entityManager.getTransaction().begin();
        List<Address> addresses = this.entityManager.createQuery("FROM Address ORDER BY size(employees) DESC,town_id ASC",Address.class).setMaxResults(10).getResultList();

        for (Address address : addresses) {
            System.out.println(String.format("%s, %s - %d employees",address.getText(),address.getTown().getName(),address.getEmployees().size()));
        }

        this.entityManager.getTransaction().commit();
    }

    /*
    8.Get Employee with Project
     */
    private void EmployeeWithProject()
    {
        Scanner scanner = new Scanner(System.in);

        int id = Integer.parseInt(scanner.nextLine());

        this.entityManager.getTransaction().begin();

        Employee employee = this.entityManager.createQuery("FROM Employee WHERE employee_id = :id",Employee.class).setParameter("id",id).getSingleResult();
        //List<Project> projects = this.entityManager.createQuery("FROM Project WHERE projects = 147 ORDER BY name ASC",Project.class).getResultList();
        System.out.println(String.format("%s %s - %s",employee.getFirstName(),employee.getLastName(),employee.getJobTitle()));

        employee.getProjects().stream().sorted(Comparator.comparing(Project::getName)).forEach(p->{
            System.out.println(String.format("        %s",p.getName()));
        });

        this.entityManager.getTransaction().commit();
    }

    /*
    9.Find Latest 10 Projects
     */
    private void  FindLatestProjects()
    {
        this.entityManager.getTransaction().begin();

        List<Project> projects = this.entityManager.createQuery("FROM Project ORDER BY start_date DESC",Project.class).setMaxResults(10).getResultList();

        projects.stream().sorted(Comparator.comparing(Project::getName)).forEach(p->{
            System.out.println(String.format("Project name: %s", p.getName()));
            System.out.println(String.format("        Project Description: %s", p.getDescription()));
            System.out.println(String.format("        Project Start Date: %s", p.getStartDate()));
            System.out.println(String.format("        Project End Date: %s", p.getEndDate()));
        });

        this.entityManager.getTransaction().commit();

    }

    /*
    10.Increase Salaries
     */
    private void IncreaseSalaries()
    {
        this.entityManager.getTransaction().begin();

        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE department_id IN(1,2,4,11)",Employee.class).getResultList();
        List<Employee> increasedSalaryEmployees= new ArrayList<>();
        for (Employee employee : employees) {

            employee.setSalary(employee.getSalary().multiply(new BigDecimal(1.12)));
            this.entityManager.persist(employee);
            increasedSalaryEmployees.add(employee);
        }

        for (Employee employee : increasedSalaryEmployees) {
            System.out.println(String.format("%s %s ($%.2f)",employee.getFirstName(),employee.getLastName(), employee.getSalary()));
        }

        this.entityManager.getTransaction().commit();
    }
    /*
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11.Remove Towns
     */
    private void RemoveTowns()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter town to be deleted:");
        String input = scanner.nextLine();

        this.entityManager.getTransaction().begin();

        Town towns = this.entityManager.createQuery("FROM Town WHERE name = :town",Town.class).setParameter("town",input).getSingleResult();
        List<Address>  addresses = this.entityManager.createQuery("FROM Address WHERE town.name = :input",Address.class).setParameter("input",input).getResultList();

        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE address.town.name = :input",Employee.class).setParameter("input",input).getResultList();

        for (Employee employee : employees) {
            employee.setAddress(null);
        }
        for (Address address : addresses) {
            this.entityManager.remove(address);

        }

        this.entityManager.remove(towns);

        this.entityManager.getTransaction().commit();
        if(addresses.size()>1)
        {
            System.out.println(String.format("%d addresses in %s deleted",addresses.size(),towns.getName()));
        }else
        {
            System.out.println(String.format("%d address in %s deleted",addresses.size(),towns.getName()));
        }
    }
    /*
    12.Find Employees by First Name
     */
    private void FindEmployeesByFirstName()
    {
        Scanner scanner=new Scanner(System.in);
        String input  = scanner.nextLine();
        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE first_name LIKE concat(:input,'%')",Employee.class).setParameter("input",input).getResultList() ;

        for (Employee employee : employees) {

            System.out.println(String.format("%s %s - %s - ($%.2f)",employee.getFirstName(),employee.getLastName(),employee.getDepartment().getName(),employee.getSalary()));

        }

        this.entityManager.getTransaction().commit();
    }
    /*
    13.Employees Maximum Salaries
     */
    private void EmployeesMaximumSalaries()
    {
        this.entityManager.getTransaction().begin();

        List<Employee> departments = this.entityManager.createQuery("FROM Employee GROUP BY department_id HAVING NOT MAX(salary) BETWEEN 30000 AND 70000",Employee.class).getResultList();

        for (Employee department : departments) {
            System.out.println(String.format("%s - %.2f",department.getDepartment().getName(),department.getSalary()));
        }

        this.entityManager.getTransaction().commit();
    }

}
