package lab6.crm.dto;

import java.util.List;

public class ApplicationRequestDto {
    private Long id;
    private String userName;
    private Long courseId;
    private String commentary;
    private String phone;
    private boolean handled;
    private List<Long> operatorIds; // optional

    public ApplicationRequestDto() {}

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public List<Long> getOperatorIds() {
        return operatorIds;
    }

    public void setOperatorIds(List<Long> operatorIds) {
        this.operatorIds = operatorIds;
    }
}
