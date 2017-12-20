package com.ttn.blognet.dto;

import com.adobe.cq.sightly.WCMUsePojo;
import com.ttn.blognet.service.BlogService;

import java.util.List;


/**
 * Created by vishal on 20/12/17.
 */
public class BlogListing extends WCMUsePojo {

	private List<Blog> blogs;

	//@Reference BlogService blogService;

	public void activate() throws Exception {
		String param = get("sort", String.class);

		System.out.println("sort param is ----------------------");
		System.out.println(param);
		BlogService blogService = getSlingScriptHelper().getService(BlogService.class);
		blogs = blogService.getBlogList(param);

	}

	public List<Blog> getBlogs() {
		return this.blogs;
	}
}
