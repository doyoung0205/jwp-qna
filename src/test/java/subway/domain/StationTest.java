package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StationTest {
    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        final Station station = new Station("잠실역");
        final Station actual = stations.save(station);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findByName("잠실역");
        assertThat(station1.getId()).isEqualTo(station2.getId());
        assertThat(station1.getName()).isEqualTo(station2.getName());

        assertThat(station2).isSameAs(station2);
        assertThat(station2).isEqualTo(station2);
    }

    @Test
    void findById() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findById(station1.getId()).get();
        assertThat(station1.getId()).isEqualTo(station2.getId());
        assertThat(station1.getName()).isEqualTo(station2.getName());

        // 완전 동일 한 객체
        assertThat(station2).isSameAs(station2);
        assertThat(station2).isEqualTo(station2);
    }

    @Test
    void update() {
        final Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        // 바로 데이터베이스에 접근하면서 영속성에 있는 객체들을 flush 함.
        final Station station2 = stations.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }
}
