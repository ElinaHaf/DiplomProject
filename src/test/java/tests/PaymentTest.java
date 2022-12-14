package tests;

import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.data.RestApiHelper;
import ru.netology.data.SQLHelper;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class PaymentTest {
    MainPage mainPage = open("http://localhost:8080/", MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessTransactionWithApprovedPaymentCardThroughAPI() {
        var cardInfo = DataHelper.getValidApprovedCardData();
        RestApiHelper.createCard(cardInfo);
        var paymentCardData = SQLHelper.getPaymentCardData();
        assertEquals("APPROVED", paymentCardData.getStatus());
    }

    @Test
    void shouldSuccessTransactionWithDeclinedPaymentCardThroughAPI() {
        var cardInfo = DataHelper.getValidDeclinedCardData();
        RestApiHelper.createCard(cardInfo);
        var paymentCardData = SQLHelper.getPaymentCardData();
        assertEquals("DECLINED", paymentCardData.getStatus());
    }

    @Test
    void shouldSuccessTransactionApprovedCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    void shouldSuccessTransactionWithDeclinedCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    //card
    @Test
    void shouldDeclineWithRandomPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithNotApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("????????????! ???????? ?????????????? ?? ???????????????????? ????????????????");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithCardNumberFieldAreZero();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("????????????! ???????? ?????????????? ?? ???????????????????? ????????????????");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithEmptyCardNumber();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithCardNumberSpecialCharacters();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("???????????????? ????????????");
    }

    //month
    @Test
    void shouldDeclineWithFalseMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("90","22");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("?????????????? ???????????? ???????? ???????????????? ??????????");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllMonthNumberFieldAreZero();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumberField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllMonthNumberFieldAreEmpty();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllMonthNumberFieldAreSpecialCharacters();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonthField("???????????????? ????????????");
    }

    //year
    @Test
    void shouldDeclineWithFalseYear() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("10","88");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("?????????????? ???????????? ???????? ???????????????? ??????????");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllYearNumberFieldAreZero();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField ("?????????? ???????? ???????????????? ??????????");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllYearNumberFieldAreEmpty();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllYearNumberFieldAreSpecialCharacters();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYearField("???????????????? ????????????");
    }

    //name
    @Test
    void shouldDeclineWithFalseName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("???????????? ????????????");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("0000 00000");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("???????? ?????????????????????? ?????? ????????????????????");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("%::.;;(");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwnerField("???????????????? ????????????");
    }

    //cvc
    @Test
    void shouldDeclineWithFalseCvc() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFalse();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreZero() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFieldAreZero();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreEmpty() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFieldAreEmpty();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreSpecialCharacters() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFieldAreSpecialCharacters();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvcField("???????????????? ????????????");
    }
}
