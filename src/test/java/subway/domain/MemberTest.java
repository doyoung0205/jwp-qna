package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberTest {

    @Autowired
    private MemberRepository members;
    @Autowired
    private FavoriteRepository favorites;

    @Test
    void name() {
        final Member expected = new Member("doyoung");
        expected.addFavorite(favorites.save(new Favorite()));
        final Member actual = members.save(expected);
        members.flush();
    }
}