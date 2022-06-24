package ru.netology.data;
import com.github.javafaker.Faker;
import java.util.Locale;

public class DataGenerator {

    public static class InfoDataGenerator {

        public static String generateLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }

        public static String generatePassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }
        public static String generateWrongLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }
        public static String generateWrongPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }

        public static User userInfo(String status) {
            return new User(generateLogin(), generatePassword(), status);
        }



    }
}
