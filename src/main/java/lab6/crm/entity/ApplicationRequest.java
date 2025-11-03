package lab6.crm.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ApplicationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;

    @ManyToOne
    private Courses course;

    @ManyToMany
    @JoinTable(
            name = "request_operator",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    private List<Operators> operators = new ArrayList<>();

    // ======= Конструкторы =======
    public ApplicationRequest() {}

    public ApplicationRequest(Long id, String userName, String commentary, String phone,
                              boolean handled, Courses course, List<Operators> operators) {
        this.id = id;
        this.userName = userName;
        this.commentary = commentary;
        this.phone = phone;
        this.handled = handled;
        this.course = course;
        this.operators = operators;
    }

    // ======= Геттеры / Сеттеры =======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public List<Operators> getOperators() {
        return operators;
    }

    public void setOperators(List<Operators> operators) {
        this.operators = operators;
    }

    // ======= Доп. метод =======
    public void removeOperator(Operators operator) {
        this.operators.remove(operator);
        operator.getRequests().remove(this);
    }
}
