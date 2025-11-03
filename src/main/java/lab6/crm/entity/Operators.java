package lab6.crm.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Operators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String department;

    @ManyToMany(mappedBy = "operators")
    private List<ApplicationRequest> requests = new ArrayList<>();

    // ======= Конструкторы =======
    public Operators() {}

    public Operators(Long id, String name, String surname, String department, List<ApplicationRequest> requests) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.requests = requests;
    }

    // ======= Геттеры / Сеттеры =======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<ApplicationRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<ApplicationRequest> requests) {
        this.requests = requests;
    }
}
