/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.web;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Administrator
 */
@WebListener()
public class VisitorsDataListener implements ServletContextListener {
    private ServletContext application;
    private String path="/WEB-INF/config/totalbuy.properties";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        application = sce.getServletContext();
        
        String propPath = application.getInitParameter("prop-path");
        if(propPath!=null){
            path = propPath;
        }
        path = application.getRealPath(path);
        application.log("TotalBuy 已啟動:" + path);
        
        File file = new File(path);
        if (file.exists()) {            
            Properties props = new Properties();
            try(FileReader reader = new FileReader(file)){
                props.load(reader);
                Enumeration names = props.propertyNames();
                while(names.hasMoreElements()){
                    String name = (String)names.nextElement();
                    String value = props.getProperty(name);
                    if(value.matches("\\d+")){
                        application.setAttribute(name, Integer.parseInt(value));
                    }else{
                        if(name.equals("app.visitors")){
                            throw new RuntimeException();
                        }else{
                            application.setAttribute(name, value);
                        }
                    }
                }
            }catch(Exception ex){
                application.setAttribute("app.visitors", 30);
            }
        }else{
            application.setAttribute("app.visitors", 500);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
        Enumeration<String> names = application.getAttributeNames();
        Properties props = new Properties();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            Object value = application.getAttribute(name);
            System.out.println(name + ": " + value);
            if(name.indexOf("app.")==0){
                props.setProperty(name, value.toString());                
            }
        }
        
        //String path = application.getRealPath("/WEB-INF/config/totalbuy.properties");
        application.log("TotalBuy 已停止:" + path);
        
        File file = new File(path);        
        try(FileWriter writer = new FileWriter(file)){
            props.store(writer, "TotalBuy Configures");
            application.log("系統設定已儲存: " + file.getAbsolutePath());
        }catch(IOException ex){
            application.log("無法儲存系統設定: " + file.getAbsolutePath(), ex);
        }
    }
}
