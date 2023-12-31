package test;

import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthoTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Successful authorization of the registered user")
    void shouldSuccessfulAuthorizationRegisteredUser() {
        var newUser = DataGenerator.Registration.newUser("active");
        DataGenerator.patternRequest(newUser);
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $(".button").click();
        $(".heading").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Личный кабинет"));

    }

    @Test
    @DisplayName("Not successful authorization if not registered user")
    void shouldNotSuccessfulAuthorizationRegisteredUser() {
        var newUser = DataGenerator.Registration.newUser("active");
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $(".button").click();
        $(".notification_status_error .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Not successful authorization if user blocked")
    void shouldNotSuccessfulAuthorizationIfUserBlocked() {
        var newUser = DataGenerator.Registration.newUser("blocked");
        DataGenerator.patternRequest(newUser);
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $(".button").click();
        $(".notification_status_error .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! " + "Пользователь заблокирован"));

    }

    @Test
    @DisplayName("Not successful authorization if not valid password")
    void shouldNotSuccessfulAuthorizationIfNotValidPassword() {
        var newUser = DataGenerator.Registration.newUser("blocked");
        DataGenerator.patternRequest(newUser);
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.generatePassword());
        $(".button").click();
        $(".notification_status_error .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Not successful authorization if not valid login")
    void shouldNotSuccessfulAuthorizationIfNotValidLogin() {
        var newUser = DataGenerator.Registration.newUser("blocked");
        DataGenerator.patternRequest(newUser);
        $("[data-test-id='login'] input").setValue(DataGenerator.generateLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $(".button").click();
        $(".notification_status_error .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"));

    }


}
