package beevengers.billiardmanager.entity.product;

import beevengers.billiardmanager.entity.Image;
import beevengers.billiardmanager.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nationalized
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private ProductType type;

    @Column(name = "unit", nullable = false)
    private String unit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "hourly", nullable = false)
    private Boolean hourly = false;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "product", cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<ProductCancellation> productCancellations = new ArrayList<>();

}