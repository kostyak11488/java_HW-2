package ru.netology.cardorder;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardOrderTest {
    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 5000;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Успешная отправка формы с валидными данными")
    void shouldSubmitFormSuccessfully() {
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id=order-success]")
                .shouldBe(visible)
                .shouldHave(text("Ваша заявка успешно отправлена"));
    }

    @Test
    @DisplayName("Ошибка при невалидном телефоне")
    void shouldShowErrorForInvalidPhone() {
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("89991234567");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    @DisplayName("Ошибка при неотмеченном чекбоксе")
    void shouldShowErrorForUncheckedAgreement() {
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("button").click();

        $("[data-test-id=agreement].input_invalid")
                .shouldBe(visible);
    }
}

