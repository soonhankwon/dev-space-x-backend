package soon.devspacexbackend.series.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SeriesType type;

    @Enumerated(EnumType.STRING)
    private SeriesStatus status;

    public String getName() {
        return name;
    }
}
