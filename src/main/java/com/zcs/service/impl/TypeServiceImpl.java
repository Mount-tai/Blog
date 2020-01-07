package com.zcs.service.impl;

import com.zcs.dao.TypeRepository;
import com.zcs.exception.NotFoundException;
import com.zcs.po.Type;
import com.zcs.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeRepository typeRepository;


    //保存
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }


    //根据id获取
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }


    //根据name查询
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }


    //查询所有Type并分页
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    //查询所有Type
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    //根据添加更新时间 获取最新 Type
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return typeRepository.findTop(pageable);
    }


    //更新
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).get();
        if (t == null) {
            throw new NotFoundException("此类型不存在");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }


    //删除
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
