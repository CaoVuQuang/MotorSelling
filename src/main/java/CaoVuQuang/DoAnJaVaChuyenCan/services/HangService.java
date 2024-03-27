package CaoVuQuang.DoAnJaVaChuyenCan.services;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.Category;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Hang;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.ICategoryRepository;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.IHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangService {
    @Autowired
    private IHangRepository hangRepository;

    public List<Hang> getAllHangs(){
        return hangRepository.findAll();
    }

    public Hang getHangById (Long id){
        return hangRepository.findById(id).orElse(null);
    }

    public Hang saveHang(Hang hang){
        return hangRepository.save(hang);
    }

    public void deleteHang(Long id){
        hangRepository.deleteById(id);
    }
}
