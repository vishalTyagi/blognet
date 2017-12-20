package com.ttn.blognet.dto;

import com.adobe.cq.sightly.WCMUsePojo;
import org.apache.sling.api.resource.Resource;


/**
 * Created by vishal on 20/12/17.
 */
public class BlogView extends WCMUsePojo {

	private Blog blog;
	public void activate() throws Exception {
		Resource resource = getResource();
		blog = new Blog(resource.getValueMap());
	}

	public Blog getBlog(){
		return this.blog;
	}
}
