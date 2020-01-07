package com.zcs.service.impl;

import com.zcs.dao.BlogRepository;
import com.zcs.exception.NotFoundException;
import com.zcs.po.Blog;
import com.zcs.po.Type;
import com.zcs.service.BlogService;
import com.zcs.util.MarkdownUtils;
import com.zcs.util.MyBeanUtils;
import com.zcs.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogrepository;


    //根据id查询blog
    @Override
    public Blog getBlog(Long id) {
        return blogrepository.findById(id).get();
    }




    //根据id查询，并转换文本为 html格式，从而不修改数据库
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogrepository.findById(id).get();
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogrepository.updateViews(id);
        return b;
    }


    /**使用Specification对象 进行复杂查询
     * 判断title是否为空
     * 判断分类是否为空
     * 判断是否推荐
     */

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogrepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }



    //分页查询所有blog
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogrepository.findAll(pageable);
    }


    //根据标签查询blog
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogrepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?>  cq,CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }


    //根据字符串搜索
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogrepository.findByQuery(query, pageable);
    }


    //最近更新或添加最近时间搜索
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogrepository.finTop(pageable);
    }


    //恩据时间搜索，并分类
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogrepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogrepository.findByYear(year));
        }
        return map;
    }


    //计算blog总数
    @Override
    public Long countBlog() {
        return blogrepository.count();
    }



    //保存
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogrepository.save(blog);
    }




    //更新
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogrepository.findById(id).get();
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogrepository.save(b);
    }






    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogrepository.deleteById(id);
    }
}
