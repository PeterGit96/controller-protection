package co.develhope.controllerprotection.auth.dto;

public class RequestPasswordDTO {

    private String email;

    public RequestPasswordDTO() { }

    public RequestPasswordDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
