/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Employee;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author grier
 */

/*
Clase FileManager para guardar y cargar empleados desde un archivo de texto.
Actualmente no es obligatorio usarla, pero se incluye como funcionalidad extra.
 */
public class FileManager {

    private static final String FILE_PATH = "empleados.txt";

    // Método para guardar una lista de empleados en un archivo
    public void saveEmployees(ArrayList<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee emp : employees) {
                writer.write(emp.getId() + "," + emp.getName() + "," + emp.getAge() + "," + emp.getDepartment() + "," + emp.getSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar una lista de empleados desde un archivo
    public ArrayList<Employee> loadEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String department = parts[3];
                    double salary = Double.parseDouble(parts[4]);

                    Employee emp = new Employee(id, name, age, department, salary);
                    employees.add(emp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
