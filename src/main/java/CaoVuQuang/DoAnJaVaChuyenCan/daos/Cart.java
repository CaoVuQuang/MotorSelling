package CaoVuQuang.DoAnJaVaChuyenCan.daos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();

    public void addItems(Item item){
        boolean isExits = cartItems.stream()
                .filter(i -> Objects.equals(i.getXeId(),
                        item.getXeId())).findFirst().map(i ->{
                            i.setQuantity(i.getQuantity() + item.getQuantity());
                            return true;
                })
                .orElse(false);
        if (!isExits){
            cartItems.add(item);
        }
    }

    public void removeItems(Long xeId){
        cartItems.removeIf(item -> Objects.equals(item.getXeId(), xeId));
    }

    public void updateItems(Long xeId, int quantity){
        cartItems.stream()
                .filter(item -> Objects.equals(item.getXeId(), xeId))
                .forEach(item -> item.setQuantity(quantity));
    }
}
