package techcourse.myblog.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import techcourse.myblog.service.SignUpException;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static techcourse.myblog.service.UserServiceTest.VALID_PASSWORD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    int randomPortNumber;

    @Autowired
    WebTestClient webTestClient;

    @Test
    @DisplayName("로그인 페이지를 보여준다.")
    void showLoginPage() {
        webTestClient.get()
                .uri("/users/login")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("회원가입 페이지를 보여준다.")
    void showRegisterPage() {
        webTestClient.get()
                .uri("/users/sign-up")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("유저 목록을 보여준다.")
    void showUserList() {
        webTestClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("회원 가입 페이지에서 유저 정보를 넘겨받아 새로운 유저를 생성한다.")
    void createUser() {
        String name = "hibri";
        String email = "test1@woowa.com";
        String password = VALID_PASSWORD;
        String passwordConfirm = VALID_PASSWORD;

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .consumeWith(response -> {
                    URI location = response.getResponseHeaders().getLocation();
                    assertThat(location.toString())
                            .isEqualTo("http://localhost:" + randomPortNumber + "/users/login");
                });
    }

    @Test
    @DisplayName("email이 중복되는 경우 error message를 담은 페이지를 되돌려준다.")
    void isDuplicatedEmail() {
        String name = "Deock";
        String email = "test2@woowa.com";
        String password = VALID_PASSWORD;
        String passwordConfirm = VALID_PASSWORD;

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isFound();

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.EMAIL_DUPLICATION_MESSAGE);
                });
    }

    @Test
    @DisplayName("이름이 2자 미만인 경우 error message를 담은 페이지를 되돌려준다.")
    void underValidNameLength() {
        String name = "a";
        String email = "test3@woowa.com";
        String password = VALID_PASSWORD;
        String passwordConfirm = VALID_PASSWORD;

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.INVALID_NAME_LENGTH_MESSAGE);
                });
    }

    @Test
    @DisplayName("이름이 10자 초과인 경우 error message를 담은 페이지를 되돌려준다.")
    void exceedValidNameLength() {
        String name = "abcdefghijk";
        String email = "test4@woowa.com";
        String password = VALID_PASSWORD;
        String passwordConfirm = VALID_PASSWORD;

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.INVALID_NAME_LENGTH_MESSAGE);
                });
    }

    @Test
    @DisplayName("잘못된 이름인 경우 error message를 담은 페이지를 되돌려준다.")
    void checkInvalidName() {
        String name = "afghij1";
        String email = "test4@woowa.com";
        String password = VALID_PASSWORD;
        String passwordConfirm = VALID_PASSWORD;

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.NAME_INCLUDE_INVALID_CHARACTERS_MESSAGE);
                });
    }

    @Test
    @DisplayName("비밀번호 길이가 8자 미만인 경우 error message를 담은 페이지를 되돌려준다.")
    void checkInvalidPasswordLength() {
        String name = "name";
        String email = "test5@woowa.com";
        String password = "passwor";
        String passwordConfirm = "passwor";

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.INVALID_PASSWORD_LENGTH_MESSAGE);
                });
    }

    @Test
    @DisplayName("잘못된 비밀번호인 경우 error message를 담은 페이지를 되돌려준다.")
    void checkInvalidPassword() {
        String name = "name";
        String email = "test5@woowa.com";
        String password = "wrong password";
        String passwordConfirm = "wrong password";

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.INVALID_PASSWORD_MESSAGE);
                });
    }

    @Test
    @DisplayName("비밀번호 확인과 비밀번호가 다른 경우 에러 메시지를 담은 페이지를 되돌려준다.")
    void confirmPassword() {
        String name = "name";
        String email = "test5@woowa.com";
        String password = VALID_PASSWORD;
        String passwordConfirm = VALID_PASSWORD + "diff";

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password)
                        .with("passwordConfirm", passwordConfirm))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains(SignUpException.PASSWORD_CONFIRM_FAIL_MESSAGE);
                });
    }
}