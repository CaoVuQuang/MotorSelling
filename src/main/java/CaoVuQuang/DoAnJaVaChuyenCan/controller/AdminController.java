package CaoVuQuang.DoAnJaVaChuyenCan.controller;

import CaoVuQuang.DoAnJaVaChuyenCan.entity.Hang;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Xe;
import CaoVuQuang.DoAnJaVaChuyenCan.entity.Category;
import CaoVuQuang.DoAnJaVaChuyenCan.repository.IXeRepository;
import CaoVuQuang.DoAnJaVaChuyenCan.services.HangService;
import CaoVuQuang.DoAnJaVaChuyenCan.services.XeService;
import CaoVuQuang.DoAnJaVaChuyenCan.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private XeService xeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HangService hangService;

    @Autowired
    private IXeRepository xeRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')") // Yêu cầu quyền ROLE_ADMIN để truy cập
    @GetMapping("/admin")
    public String adminPage() {
        return "Welcome to the admin page!";
    }

    @GetMapping("/list")
    public String showAllXes(@NotNull Model model,
                             @RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "30") Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy){
        model.addAttribute("xemays", xeService.getAllXes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        model.addAttribute("totalPages", xeService.getAllXes(pageNo, pageSize, sortBy).size() / pageSize);
        return "admin/list";
    }


    @GetMapping("/listCategory")
    public String showAllHoaDon(@NotNull Model model,
                             @RequestParam(defaultValue = "0") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy){
        model.addAttribute("xemays", xeService.getAllXes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        model.addAttribute("totalPages", xeService.getAllXes(pageNo, pageSize, sortBy).size() / pageSize);
        return "admin/listCategory";
    }

    /*Thêm*/
    @GetMapping("/add")
    public String addXeForm(Model model) {
        model.addAttribute("xe", new Xe());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        return "admin/add";
    }

    @PostMapping("/add")
    public String addXe(@ModelAttribute Xe xe) {
        xeRepository.save(xe);
        return "redirect:/admin/list";
    }

    /*Thêm*/
    @GetMapping("/addCategory")
    public String addCateForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        return "admin/addCategory";
    }

    @PostMapping("/addCategory")
    public String addCate(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/listCategory";
    }


    @GetMapping("/delete/{id}")
    public String deleteXe(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        BindingResult result = xeService.validateDeleteXe(id);

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa xe do có các hàng con được tham chiếu đến.");
        } else {
            xeService.deleteXe(id);
            redirectAttributes.addFlashAttribute("success", "Xóa xe thành công.");
        }

        return "redirect:/admin/list";
    }

    @GetMapping("/deleteCate/{id}")
    public String deleteCate(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/listCategory";
    }


    /*edit*/
    @GetMapping("/edit/{id}")
    public String editXe(@PathVariable Long id, Model model) {
        Xe xe = xeService.getXeById(id);
        List<Category> categories = categoryService.getAllCategories();
        List<Hang> hangs = hangService.getAllHangs();
        model.addAttribute("xe", xe);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Xe editProduct,
                       @RequestParam MultipartFile imageProduct,
                       BindingResult result,
                       Model model)
    {
        if (result.hasErrors()) {
            model.addAttribute("xe", editProduct);
            model.addAttribute("listCategory", categoryService.getAllCategories() );
            model.addAttribute("listHang", hangService.getAllHangs() );
            return "/admin/edit";
        }
        if(imageProduct != null && imageProduct.getSize() > 0)
        {
            try {
                File saveFile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + editProduct.getImage());
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        xeService.edit(editProduct);
        return "redirect:/xemays";
    }

    /*save*/
    @PostMapping("/save")
    public String saveXe(@Valid @ModelAttribute("xe") Xe xe,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        String viewName = "xe/add";
        if (xe.getId() != null) {
            viewName = "xe/edit";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", (xe.getId() != null) ? "Edit Xe" : "Add Xe");
            return viewName;
        }

        xeService.addXe(xe);
        redirectAttributes.addFlashAttribute("message", "Xe saved successfully");
        return "redirect:/admin/list";
    }

    @GetMapping("/search")
    public String searchXe(@NotNull Model model,
                           @RequestParam String keyword,
                           @RequestParam(defaultValue = "0") Integer pageNo,
                           @RequestParam(defaultValue = "20") Integer pageSize,
                           @RequestParam(defaultValue = "id") String sortBy){
        model.addAttribute("xemays", xeService.searchXe(keyword));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", xeService.getAllXes(pageNo, pageSize, sortBy).size()/pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("hangs", hangService.getAllHangs());
        return "admin/list";
    }


}
