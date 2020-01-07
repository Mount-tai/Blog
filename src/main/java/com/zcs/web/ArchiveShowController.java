package com.zcs.web;

import com.zcs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//归档
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;


    /*----------------------------------归档-----------------------------------------*/
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
