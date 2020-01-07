package com.zcs.service.impl;

import com.zcs.dao.TagRepository;
import com.zcs.exception.NotFoundException;
import com.zcs.po.Tag;
import com.zcs.po.Type;
import com.zcs.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private TagRepository tagRepository;


    //保存
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }


    //根据blog  Id 获取  Tag
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }


    //根据Tag name 获取Tag
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    //获取所有Tag，并分页
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);

    }


    //获取所有tag
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }


    //根据添加更新时间 获取最新 Tag
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return tagRepository.findTop(pageable);
    }


    //根据字符串查询相似 Tag
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }




    //更新
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = tagRepository.findById(id).get();
        if (tag1 == null) {
            throw new NotFoundException("此类型不存在");
        }
        BeanUtils.copyProperties(tag, tag1);
        return tagRepository.save(tag1);
    }



    //删除
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
