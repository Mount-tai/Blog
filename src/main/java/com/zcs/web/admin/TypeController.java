package com.zcs.web.admin;

import com.zcs.po.Type;
import com.zcs.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


//分类编辑
@Controller
@RequestMapping("/admin")
public class TypeController {


    @Autowired
    private TypeService typeService;


    /*----------------------------------分类管理页面，分页展示所有分类-----------------------------------------*/
    @GetMapping("/types")
    public String types(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /*----------------------------------分类管理页面 分类新增-----------------------------------------*/

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }


    /*----------------------------------分类管理页面 分类编辑-----------------------------------------*/
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }


    /*----------------------------------分类新增-----------------------------------------*/
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该名称不能重复");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);
        if (type1 == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }


    /*----------------------------------分类编辑-----------------------------------------*/
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该名称不能重复");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id, type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }


    /*----------------------------------删除分类-----------------------------------------*/
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
