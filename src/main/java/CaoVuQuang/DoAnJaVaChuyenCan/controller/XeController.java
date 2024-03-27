package CaoVuQuang.DoAnJaVaChuyenCan.controller;

import CaoVuQuang.DoAnJaVaChuyenCan.daos.Item;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Xe;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.IXeRepository;
import CaoVuQuang.DoAnJaVaChuyenCan.services.HangService;
import CaoVuQuang.DoAnJaVaChuyenCan.services.XeService;
import CaoVuQuang.DoAnJaVaChuyenCan.services.CartService;
import CaoVuQuang.DoAnJaVaChuyenCan.services.CategoryService;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/xemays")
public class XeController {
    @Autowired
    private XeService xeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HangService hangService;

    @Autowired
    private CartService cartService;


    /*mới*/
    @GetMapping
    public String showAllXes(@NotNull Model model,
                             @RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "12") Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy){
        model.addAttribute("xemays", xeService.getAllXes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        model.addAttribute("totalPages", xeService.getAllXes(pageNo, pageSize, sortBy).size() / pageSize);
        return "xe/list";
    }
    /*mới*/


    /*giỏ hàng*/
    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session, @RequestParam long id,
                                                @RequestParam String name,
                                               /* @RequestParam String image,*/
                                                @RequestParam double price,
                                                @RequestParam(defaultValue = "1") int quantity){
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name/*, image*/ , price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/xemays";
    }

    @GetMapping("/search")
    public String searchXe(@NotNull Model model,
                           @RequestParam String keyword,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) String hang,
                           @RequestParam(defaultValue = "0") Integer pageNo,
                           @RequestParam(defaultValue = "20") Integer pageSize,
                           @RequestParam(defaultValue = "id") String sortBy) {
        List<Xe> searchResults;
        if (StringUtils.isEmpty(category) && StringUtils.isEmpty(hang)) {
            searchResults = xeService.searchXe(keyword);
        } else if (!StringUtils.isEmpty(category)) {
            searchResults = xeService.searchXeByCategory(keyword, category);
        } else {
            searchResults = xeService.searchXeByHang(keyword, hang);
        }

        model.addAttribute("xemays", searchResults);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", xeService.getAllXes(pageNo, pageSize, sortBy).size() / pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());

        return "xe/list";
    }






    private IXeRepository xeRepository;



    @GetMapping("/detail/{id}")
    public String getXeDetail(@PathVariable("id") Long id, Model model) {
        // Lấy thông tin sách từ cơ sở dữ liệu dựa trên id
        Xe xe = xeService.getXeById(id);
        model.addAttribute("xe", xe);
        return "xe/photo-detail";
    }


}




