package com.vmutter.swaggerexample.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmutter.swaggerexample.rs.SwaggerExampleRS;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

public class App {

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		try {
			Resource.setDefaultUseCaches(false);

			// Swagger
			buildSwagger();

			final HandlerList handlers = new HandlerList();
			handlers.addHandler(buildSwaggerUI());
			handlers.addHandler(buildContext());

			Server server = new Server(8080);
			server.setHandler(handlers);

			server.start();
			server.join();
		} catch (Exception e) {
			LOG.error("There was an error starting up the server.", e);
		}
	}

	private static void buildSwagger() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setResourcePackage(SwaggerExampleRS.class.getPackage().getName());
		beanConfig.setScan(true);
		beanConfig.setBasePath("/");
		beanConfig.setDescription("Swagger Description.");
		beanConfig.setTitle("Swagger Title");
	}

	private static ContextHandler buildContext() {
		ResourceConfig resourceConfig = new ResourceConfig();

		// Adding package to autoscan
		resourceConfig.packages(SwaggerExampleRS.class.getPackage().getName(),
				ApiListingResource.class.getPackage().getName());

		ServletContainer servletContainer = new ServletContainer(resourceConfig);
		ServletHolder entityBrowser = new ServletHolder(servletContainer);

		ServletContextHandler entityBrowserContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

		entityBrowserContext.setContextPath("/");
		entityBrowserContext.addServlet(entityBrowser, "/*");

		return entityBrowserContext;
	}

	private static ContextHandler buildSwaggerUI() throws Exception {
		final ResourceHandler swaggerUIResourceHandler = new ResourceHandler();
		swaggerUIResourceHandler.setResourceBase(App.class.getClassLoader().getResource("swagger").toURI().toString());

		final ContextHandler swaggerUIContext = new ContextHandler();

		swaggerUIContext.setContextPath("/docs/");
		swaggerUIContext.setHandler(swaggerUIResourceHandler);

		return swaggerUIContext;
	}
}
