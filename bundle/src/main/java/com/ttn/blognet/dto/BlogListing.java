package com.ttn.blognet.dto;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.*;


/**
 * Created by vishal on 20/12/17.
 */
public class BlogListing extends WCMUsePojo {

	private List<Blog> blogs;

	//@Reference BlogService blogService;

	public void activate() throws Exception {
		String param = get("sort", String.class);
		//BlogService blogService = getSlingScriptHelper().getService(BlogService.class);
		blogs = getBlogList(param);

	}

	public List<Blog> getBlogs() {
		return this.blogs;
	}

	public List<Blog> getBlogList() throws LoginException {
		ResourceResolver resourceResolver = getResourceResolver();
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Page page = pageManager.getPage("/content/blognet/main/createBlog");
		Iterator<Page> pageIterator = page.listChildren();
		List<Blog> blogs = new ArrayList<Blog>();
		while (pageIterator.hasNext()) {
			Page resource1 = pageIterator.next();
			Blog blog = new Blog(resource1.getContentResource().getValueMap());
			if (blog.getTitle() != null) {
				blog.setBlogPath(resource1.getPath());
				blogs.add(blog);
			}
		}
		return blogs;
	}

	public List<Blog> getBlogList(final String sort) {
		try {
			List<Blog> blogs = getBlogList();
			Collections.sort(blogs, new Comparator<Blog>() {
				public int compare(Blog o1, Blog o2) {
					if ("date".equals(sort)) {
						return o2.getPublishedDate().compareTo(o1.getPublishedDate());
					} else {
						return o1.getViewCount().intValue() - o2.getViewCount().intValue();
					}
				}
			});
			return blogs;
		} catch (LoginException e) {
			e.printStackTrace();
			return new ArrayList<Blog>();
		}
	}

}
