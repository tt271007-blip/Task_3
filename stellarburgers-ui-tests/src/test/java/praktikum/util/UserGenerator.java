package praktikum.util;

import praktikum.model.User;

import java.util.UUID;

public class UserGenerator {

    public static User randomUser() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "test_" + uuid + "@yandex.ru",
                "pass_" + uuid,
                "User_" + uuid
        );
    }

    public static User userWithShortPassword() {
        User u = randomUser();
        u.password = "12345";
        return u;
    }
}