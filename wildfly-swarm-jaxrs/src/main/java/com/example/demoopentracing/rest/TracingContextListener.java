package com.example.demoopentracing.rest;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.Configuration.SamplerConfiguration;
import com.uber.jaeger.Configuration.ReporterConfiguration;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * @author Pavol Loffay
 */
@WebListener
public class TracingContextListener implements ServletContextListener {
  public static Logger logger = Logger.getLogger(TracingContextListener.class.getName());


  @Inject
  private io.opentracing.Tracer tracer;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    logger.info("In contextInitialized, registering tracer");
    GlobalTracer.register(tracer);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {}

  @Produces
  @Singleton
  public static io.opentracing.Tracer jaegerTracer() {
    logger.info("In jaegerTracer");
    
    String serviceName = "wildfly-swarm-tracing-demo";
    SamplerConfiguration samplerConfiguration = new SamplerConfiguration(ProbabilisticSampler.TYPE, 1);
    ReporterConfiguration reporterConfiguration = new ReporterConfiguration();
    Configuration configuration = new Configuration(serviceName, samplerConfiguration, reporterConfiguration);

    Tracer tracer = configuration.getTracer();

    return tracer;
  }
}
