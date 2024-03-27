package CaoVuQuang.DoAnJaVaChuyenCan.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long xeId;
    private String Name;
    /*private String Image;*/
    private Double price;
    private int quantity;
}
