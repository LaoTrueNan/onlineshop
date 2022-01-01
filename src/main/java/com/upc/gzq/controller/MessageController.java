package com.upc.gzq.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;

@ServerEndpoint("/message")
@Controller
public class MessageController {
	private static CopyOnWriteArraySet<MessageController> message = new CopyOnWriteArraySet<MessageController>();
	private Session session = null;
	
	@OnOpen
    public void onOpen(Session session) throws IOException{
        this.session=session;
        message.add(this); 
        System.out.println(message);
    }
    @OnClose
    public void onClose(){
        message.remove(this);
    }
   
    @OnMessage
    public void onMessage(int count) {    
        System.out.println("发生变化"+count);
        try {
            sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
  
    @OnError
    public void onError(Throwable error){
        System.out.println(error);
        error.printStackTrace();
    }
    
    
    public void sendMessage() throws IOException{
        for(MessageController item: message){
            item.session.getBasicRemote().sendText("1");
        }
    }
}
