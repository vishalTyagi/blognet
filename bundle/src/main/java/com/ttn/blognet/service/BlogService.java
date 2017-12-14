package com.ttn.blognet.service;

import com.ttn.blognet.dto.Blog;
import org.apache.sling.api.resource.LoginException;

import java.util.Iterator;
import java.util.List;


/**
 * Created by vishal on 14/12/17.
 */
public interface BlogService {

	public Iterator<Blog> getBlogs() throws LoginException;

	public List<Blog> getBlogList(String sort) throws LoginException;
}
