package com.hehuaweb.web.admin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehuaweb.web.BaseResource;
import com.sun.jersey.api.view.Viewable;

/**
 * @author SunIndex
 * @since 2015/6/9
 */
@Path("/admin")
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class AdminIndexResource extends BaseResource {
  private static final Log LOG = LogFactory.getLog(AdminIndexResource.class);

  @GET
  @Produces(MediaType.TEXT_HTML)
  public Response index() {

    return Response.ok(new Viewable("index")).build();
  }
}
