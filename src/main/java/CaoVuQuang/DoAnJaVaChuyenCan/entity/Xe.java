package CaoVuQuang.DoAnJaVaChuyenCan.entity;

import CaoVuQuang.DoAnJaVaChuyenCan.Validator.annotation.ValidCategoryId;
import CaoVuQuang.DoAnJaVaChuyenCan.Validator.annotation.ValidHangId;
import CaoVuQuang.DoAnJaVaChuyenCan.Validator.annotation.ValidUserId;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;


@Data
@ToString
@Entity
@Table(name = "xe")
public class Xe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message ="Title must not be empty")
    @Size(max = 50, min=1, message = "title must be less than 50 characters")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ValidCategoryId
    private Category category;

    @ManyToOne
    @JoinColumn(name = "hang_id", referencedColumnName = "id")
    @ValidHangId
    private Hang hang;

    @ManyToOne
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    @ValidUserId
    private User user;











    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        Xe xe = (Xe) o;
        return getId() != null && Objects.equals(getId(),
                xe.getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
