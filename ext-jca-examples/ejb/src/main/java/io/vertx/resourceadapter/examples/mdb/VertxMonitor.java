package io.vertx.resourceadapter.examples.mdb;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import org.jboss.ejb3.annotation.ResourceAdapter;

import io.vertx.core.eventbus.Message;
import io.vertx.resourceadapter.inflow.VertxListener;

/**
 * Message-Driven Bean implementation class for: VertxMonitor
 */
@MessageDriven(name = "VertxMonitor",
       messageListenerInterface = VertxListener.class,
       activationConfig = {
                   @ActivationConfigProperty(propertyName = "address", propertyValue = "inbound-address"),
                   })
@ResourceAdapter("ext-jca-examples-app.ear#ext-jca-adapter-3.0.0-SNAPSHOT.rar")
public class VertxMonitor implements VertxListener {

   private Logger logger = Logger.getLogger(VertxMonitor.class.getName());

    /**
     * Default constructor.
     */
    public VertxMonitor() {
        logger.info("VertxMonitor started.");
    }

   @Override
   public <T> void onMessage(Message<T> message)
   {

      logger.info("Get a message from Vert.x: " + message.toString());
      T body = message.body();
      if (body != null)
      {
         logger.info("Body of the message: " + body.toString());
         message.reply("Hi, Got your message: " + body.toString());
      }
      else
      {
        message.reply("Hi, Got your empty message.");
      }
   }

}