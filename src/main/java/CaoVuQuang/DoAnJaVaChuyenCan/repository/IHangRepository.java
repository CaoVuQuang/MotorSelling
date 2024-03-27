package CaoVuQuang.DoAnJaVaChuyenCan.repository;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.Category;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Hang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHangRepository extends JpaRepository<Hang, Long> {
}
