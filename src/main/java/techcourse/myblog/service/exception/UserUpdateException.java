package techcourse.myblog.service.exception;

public class UserUpdateException extends RuntimeException {
    public static final String UPDATE_USER_FAIL_MESSAGE = "User update fail";

    public UserUpdateException() {
        super(UPDATE_USER_FAIL_MESSAGE);
    }
}
