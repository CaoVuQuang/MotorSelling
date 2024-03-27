package CaoVuQuang.DoAnJaVaChuyenCan.repository;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.Xe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IXeRepository extends PagingAndSortingRepository<Xe, Long>, JpaRepository<Xe, Long>  {

    default List<Xe> findAllXes(Integer pageNo,
                                Integer pageSize,
                                String sortBy) {
        return findAll(PageRequest.of(pageNo,
                pageSize,
                Sort.by(sortBy)))

                .getContent();
    }


    @Query("""
        SELECT b FROM Xe b
        WHERE b.title LIKE %?1%
        OR b.category.name LIKE %?1%
        OR b.hang.name LIKE %?1%
        """)
    List<Xe> searchXe(String keyword);
    @Query("""
        SELECT b FROM Xe b
        WHERE (b.title LIKE %?1%
        OR b.category.name LIKE %?1%)
        AND (b.category.name = ?2)
        """)
    List<Xe> searchXeByCategory(String keyword, String category);

    @Query("""
    SELECT b FROM Xe b
    WHERE (b.title LIKE %?1%
    OR b.hang.name LIKE %?1%)
    AND (b.hang.name = ?2)
    """)
    List<Xe> searchXeByHang(String keyword, String hang);


}
