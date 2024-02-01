package sd.tourismagency.controller;

public enum ResponseMsg {
    NOT_FOUND("Entity not found"),
    EMAIL_EXISTS("Email already exists"),
    DELETE_SUCCESS("Deleted successfully"),
    VACATION_NOT_FOUND("Vacation not found"),
    CLIENT_NOT_FOUND("Client not found"),
    LOGIN_SUCCESS_EMPLOYEE("Logged in successfully as employee!"),
    LOGIN_SUCCESS_ADMIN("Logged in successfully as admin!"),
    LOGIN_FIRST_EMPLOYEE("Log in as employee to access this endpoint."),
    LOGIN_FIRST_ADMIN("Log in as admin to access this endpoint."),
    LOGIN_FAIL("Incorrect username or password."),
    LOGOUT("Logged out."),
    LOGOUT_FAIL("Failed to log out."),
    RESERVATION_FAIL("Cannot make reservation. Not enough available spots.")
    ;


    private final String text;

    ResponseMsg(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
