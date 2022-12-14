package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.APIHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import data.DataHelper;
import page.MainPage;


import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {
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
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createCard(cardInfo);
        var creditCardData = SQLHelper.getCreditCardData();
        assertEquals("APPROVED", creditCardData.getStatus());
    }

    @Test
    void shouldSuccessTransactionWithDeclinedPaymentCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createCard(cardInfo);
        var creditCardData = SQLHelper.getCreditCardData();
        assertEquals("DECLINED", creditCardData.getStatus());

    }

    @Test
    void shouldSuccessTransactionWithDeclinedCard() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclinedCard();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkErrorMessDeclineFromBank();
    }

    @Test
    void shouldSuccessTransactionWithPaymentCard() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkApprovedMessFromBank();
    }

    @Test
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var toCreditCard = mainPage.creditPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkApprovedMessFromBank();
    }

    //card
    @Test
    void shouldDeclineWithRandomPaymentCard() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithNotApprovedCard();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("????????????! ???????? ?????????????? ?? ???????????????????? ????????????????");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithCardNumberFieldAreZero();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("????????????! ???????? ?????????????? ?? ???????????????????? ????????????????");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithEmptyCardNumber();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCardNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithCardNumberSpecialCharacters();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("???????????????? ????????????");
    }

    //month
    @Test
    void shouldDeclineWithFalseMonth() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("90","22");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("?????????????? ???????????? ???????? ???????????????? ??????????");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllMonthNumberFieldAreZero();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardNumberField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllMonthNumberFieldAreEmpty();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllMonthNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllMonthNumberFieldAreSpecialCharacters();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderMonthField("???????????????? ????????????");
    }

    //year
    @Test
    void shouldDeclineWithFalseYear() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("10","88");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField("?????????????? ???????????? ???????? ???????????????? ??????????");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllYearNumberFieldAreZero();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField ("?????????? ???????? ???????????????? ??????????");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllYearNumberFieldAreEmpty();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllYearNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndAllYearNumberFieldAreSpecialCharacters();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderYearField("???????????????? ????????????");
    }

    //name
    @Test
    void shouldDeclineWithFalseName() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("???????????? ????????????");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardOwnerField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("0000 00000");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardOwnerField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardOwnerField("???????? ?????????????????????? ?????? ????????????????????");
    }

    @Test
    void shouldShowErrorIfAllNameFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerNameApprovedCard("%::.;;(");
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCardOwnerField("???????????????? ????????????");
    }

    //cvc
    @Test
    void shouldDeclineWithFalseCvc() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFalse();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreZero() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFieldAreZero();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreEmpty() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFieldAreEmpty();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("???????????????? ????????????");
    }

    @Test
    void shouldShowErrorIfAllCvcNumberFieldAreSpecialCharacters() {
        var toCreditCard = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardIfAllCvcNumberFieldAreSpecialCharacters();
        toCreditCard.insertValidCreditCardDataForBank(cardInfo);
        toCreditCard.checkWarningUnderCvcField("???????????????? ????????????");
    }
}
}
