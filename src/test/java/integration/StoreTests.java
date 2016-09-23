package integration;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import t1.entity.Book;
import t1.entity.Catalog;
import t1.repository.Store;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by admin on 9/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StoreTests {

   static Store store = new Store();;
   static  String path ="test.xml";
    @BeforeClass
    public static void setup() throws FileNotFoundException {
        File file  = new File( path);
        if(file.exists())file.delete();
                store.setPath(path);
    }

   @Test
           public void addElements() throws Exception {
       Book b = new Book();

       b.setId("first");
       b.setTitle("XML Developer's Guide");
       b.setAuthor("Gambardella, Matthew");
       b.setDescription("An in-depth look at creating applications \n" +
               "      with XML");
       b.setGenre("Computer");
       b.setPrice("44.95");
       b.setPublishDate("12/26/20011");
      store.addOrUpdateBook(b);   ;

       Book b1 = new Book();

       b1.setId("second");
       b1.setTitle("XML D");
       b1.setAuthor("Gambardella, Matthew");
       b1.setDescription("An " +
               "      with XML");
       b1.setGenre("Computer1");
       b1.setPrice("44.95");
      store.addOrUpdateBook(b1);
       assertEquals("must be two elements", 2, store.getBooksCount());
       assertEquals("",b1,store.getBook("second"));
       assertEquals("must be two elements", 2, getDataFromXMLFile().getCatalog().size());

   }
    @Test
    public void addNewElementWithoutID() throws Exception {
        Book new1 = new Book();
        new1.setTitle("unTitle");
       store.addOrUpdateBook(new1);
        assertEquals("must be two elements", 3, getDataFromXMLFile().getCatalog().size());
    }

    @Test
    public void updateElement() throws Exception {
        Book up1 = new Book();
        up1.setId("second");
        up1.setTitle("unTitle  - non any xml");
        store.addOrUpdateBook(up1);
        assertEquals("must be two elements", 3, getDataFromXMLFile().getCatalog().size());
    }



    private   Catalog  getDataFromXMLFile() throws JAXBException {
        File file = new File(path);

        JAXBContext     jaxbContext = JAXBContext.newInstance(Catalog.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Catalog catalog = (Catalog) jaxbUnmarshaller.unmarshal(file);

            return catalog;
    }




}
