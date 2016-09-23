package t1.repository;


import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import t1.entity.Book;
import t1.entity.Catalog;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by admin on 9/21/2016.
 */

@Component
public class Store {


    private final Logger log = LoggerFactory.getLogger(Store.class);

    public Store(){}

 private   ConcurrentHashMap<String, Book> store  = new ConcurrentHashMap<String, Book>();


    @Value("${path}")
    private   String path;

    public  String getPath() {
        return path;
    }

    public  void setPath(String path) {
        this.path = path;
    }


    @PostConstruct
    private void  init(){

        getDataFromXMLFile(path);
    }

    private  synchronized void getDataFromXMLFile(String path) {
        File file = new File( path );
       if( file.exists()){
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Catalog.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Catalog catalog = (Catalog)jaxbUnmarshaller.unmarshal( file );
            for (Book book:catalog.getCatalog())
            {
                store.put(book.getId(),book);

            }
        } catch (JAXBException e) {
            e.printStackTrace();
            for (StackTraceElement ste : e.getStackTrace()) {
                log.error(e.toString());

            }
        }
    }else try {
           file.createNewFile();
       } catch (IOException e) {
           for (StackTraceElement ste : e.getStackTrace()) {
               log.error(e.toString());
           }
       }
    }

private   void putDataToXMLFromStore(String path){

    JAXBContext jaxbContext = null;
    HashSet<Book> sett = new HashSet<Book>();
    sett.addAll(this.getBooksCollection());
    Catalog ct = new Catalog();
    ct.setCatalog(sett);

    try {
        jaxbContext = JAXBContext.newInstance(Catalog.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal( ct, new File(path));
    } catch (JAXBException e) {
        for (StackTraceElement ste : e.getStackTrace()) {
            log.error(e.toString());
        }
    }





}
    public Book getBook(String id){

    return store.get(id);
}
public int getBooksCount(){

    return store.size();
}

    public  Collection<Book> getBooksCollection(){

        return store.values();
    }

    public Book addOrUpdateBook(Book book){


     String id;
      if (book.getId()==null){
          id = (new Long(new Date().getTime())).toString();
          book.setId(id);
          store.put(id, book);
      }
          else if ( store.get(book.getId())== null){
          id = book.getId();
          store.put(book.getId(), book);

      }  else{
          id = book.getId();
          update(book,store.get(book.getId())) ;

      }
        putDataToXMLFromStore(path);
       return store.get(id);
    }

    private   void update(Book newData, Book storeBook) {

        try {
            NullAwareBeanUtilsBean nb = new NullAwareBeanUtilsBean();
            nb.copyProperties(storeBook, newData);
        } catch (IllegalAccessException e) {
            for (StackTraceElement ste : e.getStackTrace()) {
                log.error(e.toString());
            }
        } catch (InvocationTargetException e) {
            for (StackTraceElement ste : e.getStackTrace()) {
                log.error(e.toString());
            }
        }


    }

}

class NullAwareBeanUtilsBean extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if(value==null)return;
        super.copyProperty(dest, name, value);
    }

}