package techcourse.myblog.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import techcourse.myblog.domain.User.User;
import techcourse.myblog.exception.UserCreationException;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static final long ID = 1L;
    private static final String NAME = "SAMPLE";
    private static final String EMAIL = "sample@mail.com";
    private static final String PASSWORD = "123qweASD!";

    @Test
    public void 동시성_테스트() {
        User user = new User(NAME, EMAIL, PASSWORD);
        User other = new User(NAME, EMAIL, PASSWORD);
        assertEquals(user, other);
    }

    @Test
    @DisplayName("수정_테스트")
    public void updateTest() {
        final String modifyName = NAME + "mo";
        User actual = new User(NAME, EMAIL, PASSWORD);
        actual = actual.update(modifyName);

        User expected = new User(modifyName, EMAIL, PASSWORD);
        assertEquals(expected, actual);
    }

    @Test
    public void 이름이_형식을_위반할때_예외발생() {
        assertThrows(UserCreationException.class, () -> {
            new User("123", EMAIL, PASSWORD);
        });
    }

    @Test
    public void 비밀번호가_형식을_위반할때_예외발생() {
        assertThrows(UserCreationException.class, () -> {
            new User(NAME, EMAIL, "1234");
        });
    }

    @Test
    public void 비밀번호가_같은지() {
        User actual = new User(NAME, EMAIL, PASSWORD);
        assertTrue(actual.isSamePassword(PASSWORD));
    }
}