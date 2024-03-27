package CaoVuQuang.DoAnJaVaChuyenCan.repository;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
}
