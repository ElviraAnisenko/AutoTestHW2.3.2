package java.ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.ru.netology.delivery.data.DataGenerator;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthoTest {

    @BeforeAll
    void registeredUser() {
        var newUser=DataGenerator.Registration.newUser("active");
        var registeredUser=newUser;
        DataGenerator.patternRequest(newUser);

    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Successful authorization of the registered user")
    void shouldSuccessfulAuthorizationRegisteredUser() {
        var newUser=DataGenerator.Registration.newUser("active");
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $(".button").click();
        $(".icon_name_bank-2449").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Личный кабинет"));

    }
}
