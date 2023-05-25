package com.example.t5255.controller;

import com.example.t5255.entity.Product;
import com.example.t5255.services.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "products/index";
    }

    @GetMapping("/create")
    public String addBookForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }



    @PostMapping("/create")
    public String addBook(@Valid Product newProduct, @RequestParam MultipartFile imageProduct, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            return "products/create";
        }
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                String uploadDir = "static/images/";
                String realPath = request.getServletContext().getRealPath(uploadDir);
                String newImageFile = UUID.randomUUID() + ".png";
                java.nio.file.Path path = Paths.get(realPath + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String ff = "/static/images/" + newImageFile;
                newProduct.setImage(ff);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.add(newProduct);
        return "redirect:/products";
    }
}