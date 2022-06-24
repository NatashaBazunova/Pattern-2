package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.User;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.InfoDataGenerator.*;


public class LoginTest {
    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    public void shouldAuthorize() {
        User active = userInfo("active");
        AuthTest.setUpAll(active);
        $x("//*[contains(@name,\"login\")]").val(active.getLogin());
        $x("//*[contains(@name,\"password\")]").val(active.getPassword());
        $(".button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldNotAuthorize() {
        User blocked = userInfo("blocked");
        AuthTest.setUpAll(blocked);
        $x("//*[contains(@name,\"login\")]").val(blocked.getLogin());
        $x("//*[contains(@name,\"password\")]").val(blocked.getPassword());
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(7));
    }
    @Test
    public void shouldNotAuthorizeWithWrongLogin() {
        User active = userInfo("active");
        AuthTest.setUpAll(active);
        $x("//*[contains(@name,\"login\")]").val(generateWrongLogin());
        $x("//*[contains(@name,\"password\")]").val(active.getPassword());
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(7));
    }
    @Test
    public void shouldNotAuthorizeWithWrongPassword() {
        User active = userInfo("active");
        AuthTest.setUpAll(active);
        $x("//*[contains(@name,\"login\")]").val(active.getLogin());
        $x("//*[contains(@name,\"password\")]").val(generateWrongPassword());
        $(".button").click();
        $(withText("Ошибка!")).shouldBe(visible);
        $(".notification__content").shouldHave(text("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(7));
    }
}
