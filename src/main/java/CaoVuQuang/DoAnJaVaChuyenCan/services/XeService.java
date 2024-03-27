package CaoVuQuang.DoAnJaVaChuyenCan.services;


import CaoVuQuang.DoAnJaVaChuyenCan.entity.ItemInvoice;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Xe;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.IItemInvoiceRepository;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.IXeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class XeService {

    @Autowired
    private IXeRepository xeRepository;

    @Autowired
    private IItemInvoiceRepository itemInvoiceRepository;



    /*mới*/
    public List<Xe> getAllXes(Integer pageNo,
                              Integer pageSize,
                              String sortBy){
        return xeRepository.findAllXes(pageNo, pageSize, sortBy);
    }
    /*mới*/

    public Xe getXeById(Long id){

        return xeRepository.findById(id).orElse(null);
    }

    public void addXe(Xe xe){
        xeRepository.save(xe);
    }

    public void updateXe(Long id, Xe xe){
        xeRepository.save(xe);
    }


    public void deleteXe(Long id){
        xeRepository.deleteById(id);
    }

    /*public List<Xe> searchXe(String keyword){
        return xeRepository.searchXe(keyword);
    }*/
    public List<Xe> searchXe(String keyword){
        return xeRepository.searchXe(keyword);
    }

    public List<Xe> searchXeByCategory(String keyword, String category) {
        return xeRepository.searchXeByCategory(keyword, category);
    }

    public List<Xe> searchXeByHang(String keyword, String hang) {
        return xeRepository.searchXeByHang(keyword, hang);
    }




    public Xe get(Long id) {
        return xeRepository.findById(id).stream().findFirst().orElse(null);
    }

    public void edit(Xe editProduct) {
        Xe find = get(editProduct.getId());
        if(find!=null) {
            ///or implement clon()
            find.setTitle(editProduct.getTitle());
            find.setImage(editProduct.getImage());
            find.setPrice(editProduct.getPrice());
            //    productRepository.save(find);
        }
    }





    public BindingResult validateDeleteXe(Long id) {
        BindingResult result = new BeanPropertyBindingResult(id, "id");

        // Kiểm tra các hàng con được tham chiếu đến xe có id
        if (coHangConThamChieuDenXe(id)) {
            result.reject("error.xeHasChildItems", "Không thể xóa xe do có các hàng con được tham chiếu đến.");
        }

        return result;
    }

    private boolean coHangConThamChieuDenXe(Long id) {
        return itemInvoiceRepository.existsByXeId(id);
    }


}
