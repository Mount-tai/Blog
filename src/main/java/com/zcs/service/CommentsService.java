package com.zcs.service;

import com.zcs.po.Comment;

import java.util.List;


public interface CommentsService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
