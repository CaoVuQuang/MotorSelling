package CaoVuQuang.DoAnJaVaChuyenCan.Validator;

import CaoVuQuang.DoAnJaVaChuyenCan.Validator.annotation.ValidHangId;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Hang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;

public class ValidHangIdValidator implements ConstraintValidator<ValidHangId, Hang> {
    @Override
    public boolean isValid(Hang hang, ConstraintValidatorContext context){
        return hang !=null && hang.getId() != null;
    }
}
