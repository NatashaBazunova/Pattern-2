package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.generateLogin;
import static ru.netology.data.DataGenerator.generatePassword;


public class LoginTest {
    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    public void shouldAuthorize() {
        var registeredUser = getRegisteredUser("active");
        $x("//*[contains(@name,\"login\")]").val(registeredUser.getLogin());
        $x("//*[contains(@name,\"password\")]").val(registeredUser.getPassword());
        $(".button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldNotAuthorize() {
        var registeredUser = getRegisteredUser("blocked");
        $x("//*[contains(@name,\"login\")]").val(registeredUser.getLogin());
        $x("//*[contains(@name,\"password\")]").val(registeredUser.getPassword());
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(7));
    }

    @Test
    public void shouldNotAuthorizeWithWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = generateLogin();
        $x("//*[contains(@name,\"login\")]").val(wrongLogin);
        $x("//*[contains(@name,\"password\")]").val(registeredUser.getPassword());
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(7));
    }

    @Test
    public void shouldNotAuthorizeWithWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = generatePassword();
        $x("//*[contains(@name,\"login\")]").val(registeredUser.getLogin());
        $x("//*[contains(@name,\"password\")]").val(wrongPassword);
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(7));
    }
    @Test
    void shouldNotAuthorizeIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $x("//*[contains(@name,\"login\")]").val(generateLogin());
        $x("//*[contains(@name,\"password\")]").val(generatePassword());
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(7));
    }
}



