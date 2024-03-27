package CaoVuQuang.DoAnJaVaChuyenCan.controller;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.Invoice;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.ItemInvoice;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.User;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.IUserRepository;
import CaoVuQuang.DoAnJaVaChuyenCan.services.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String showCart(HttpSession session, @NotNull Model model){
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "xe/cart";
    }

    @GetMapping("removeFromCart/{id}")
    public  String removeFromCart(HttpSession session, @PathVariable Long id){
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }

    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session, @PathVariable Long id,
                             @PathVariable int quantity){
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        return "xe/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session){
        cartService.removeCart(session);
        return "redirect:/cart";
    }

    @GetMapping("/datHang")
    public String datHang() {
        return "/xe/xacNhan";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("name") String name,
                           @RequestParam("address") String address,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email,
                           HttpSession session) {
        Invoice invoice = new Invoice();
        invoice.setName(name);
        invoice.setAddress(address);
        invoice.setPhone(phone);
        invoice.setEmail(email);
        cartService.saveCart(session);

        return "/xe/order";
    }


}












