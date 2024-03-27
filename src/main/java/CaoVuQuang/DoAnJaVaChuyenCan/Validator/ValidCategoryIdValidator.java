package CaoVuQuang.DoAnJaVaChuyenCan.Validator;

import CaoVuQuang.DoAnJaVaChuyenCan.Validator.annotation.ValidCategoryId;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context){
        return category != null && category.getId() != null;
    }
}
