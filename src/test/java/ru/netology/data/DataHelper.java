package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@NoArgsConstructor
public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private static int validYear = Integer.parseInt(getCurrentYear()) + 1;
    private static String numberApprovedCard = "4444 4444 4444 4441";
    private static String numberDeclinedCard = "4444 4444 4444 4442";
    private static String NotApprovedCard = "1234 5678 9123 4567";
    private static String Symbols = "#$@@ @@$$ --%% ----";

    //    FIELD NUMBER

    public static CardInfo getValidApprovedCardData() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo getValidDeclinedCardData() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberDeclinedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo getAnotherBankCardNumber() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(NotApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo getEmptyCardNumber() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo("", getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo getSymbolsCardNumber() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(Symbols, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }


    // FIElD CVC
    public static CardInfo getCardApprovedCvcNumberSymbols() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, "---");
    }

    public static CardInfo getCardApprovedEmptyCVV() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, "");
    }

    public static CardInfo getCardApprovedIfAllCvcZero() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, "000");
    }
    public static CardInfo getCardApprovedIfAllCvcFalse() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, "23");
    }

    //year
    public static CardInfo generateDataWithApprovedCardAndAllYearNumberFieldAreEmpty() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(""), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndAllYearNumberFieldAreSpecialCharacters() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf("&*"), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndAllYearNumberFieldAreZero() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf("00"), randomName, randomCvc);
    }

    //month
    public static CardInfo generateDataWithApprovedCardAndAllMonthNumberFieldAreEmpty() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, "", String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndAllMonthNumberFieldAreSpecialCharacters() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, "&*", String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndAllMonthNumberFieldAreZero() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, "00", String.valueOf(validYear), randomName, randomCvc);
    }


    public static CardInfo generateDataWithRandomCardNumber() {
        var randomName = faker.name().fullName();
        var randomCardNumber = faker.number().digits(16);
        var randomCvc = faker.number().digits(3);
        return  new CardInfo(randomCardNumber, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndParametrizedMonthAndYear(String month, String year) {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return  new CardInfo(numberApprovedCard, month, year, randomName, randomCvc);
    }

    public static CardInfo generateDataWithDeclineCardAndParametrizedMonthAndYear(String month, String year) {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return  new CardInfo(numberDeclinedCard, month, year, randomName, randomCvc);
    }
    public static String getCurrentMonth() {
        LocalDate date = LocalDate.now();
        String currentMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        return currentMonth;
    }

    public static String getNotCurrentMonth() {
        LocalDate date = LocalDate.now();
        String currentMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        return currentMonth + 20;
    }

    public static CardInfo generateDataWithParamCardOwnerNameApprovedCard(String name) {
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), name, randomCvc);
    }

    public static String getCurrentYear() {
        LocalDate date = LocalDate.now();
        String currentYear = date.format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }

    public static String invalidCard() {
        return faker.numerify("#### #### #### ####");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditCardData {
        private String id;
        private String bank_id;
        private String created;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentCardData {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TableOrderEntity {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }
}

