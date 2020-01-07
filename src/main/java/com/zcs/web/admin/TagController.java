package com.zcs.web.admin;

import com.zcs.po.Tag;
import com.zcs.service.TagService;
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


//标签编辑
@Controller
@RequestMapping("/admin")
public class TagController {


    @Autowired
    private TagService tagService;



    /*----------------------------------标签管理页面，分页展示所有标签-----------------------------------------*/
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }


    /*----------------------------------标签管理页面  标签新增-----------------------------------------*/
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }



    /*----------------------------------标签管理页面  标签编辑-----------------------------------------*/
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }



    /*----------------------------------标签新增-----------------------------------------*/
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {

        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该名称不能重复");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tags1 = tagService.saveTag(tag);
        if (tags1 == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    /*----------------------------------标签编辑-----------------------------------------*/
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该名称不能重复");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tag1 = tagService.updateTag(id, tag);
        if (tag1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }



    /*----------------------------------删除标签-----------------------------------------*/
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
