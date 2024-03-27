package CaoVuQuang.DoAnJaVaChuyenCan.repository;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.ItemInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemInvoiceRepository extends JpaRepository<ItemInvoice, Long> {
    boolean existsByXeId(Long id);
}