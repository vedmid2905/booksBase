package integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import t1.entity.Book;
import t1.entity.Catalog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTests {
    private MockMvc mockMvc;
    JAXBContext jaxbContext = null;

    @Value("${path}")
    static String path;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;


   static String filecontext = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><catalog><book id=\"bk100\"><author>Corets, Eva</author><title>Maeve Ascendant</title><genre>Fantasy</genre><price>5.95</price><publish_date>2000-11-17</publish_date><description>After the collapse of a nanotechnology  society in England, the young survivors lay the   foundation for a new society.</description></book><book id=\"bk101\"><author>Corets, Eva</author><title>Maeve Ascendant</title><genre>Fantasy</genre><price>9.95</price><publish_date>2000-11-17</publish_date><description>After the collapse of a nanotechnology   society in England, the young survivors lay the   foundation for a new society.</description></book></catalog> ";

    @BeforeClass
public static void filesetup() throws FileNotFoundException, InterruptedException {

    File file  = new File( "test3.xml");
    if(file.exists())file.delete();
    PrintWriter pw = new PrintWriter( file);
    pw.write(filecontext);
        pw.flush(); pw.close();
        Thread.sleep(1000);
}

    @Before
    public void setup()  {

        this.mockMvc = webAppContextSetup(this.wac).build();
    }


    @Test
    public void a_checkElementsOnFirststart() throws Exception{

        MvcResult result =    mockMvc.perform(post("/book/changeBook").accept("application/xml").contentType(MediaType.APPLICATION_XML).content("<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +

                "</catalog>\n"))
                .andExpect(status().isOk()).andReturn();
        Catalog catalog = getCatalog(result);
        assertEquals("must be two elements",2, catalog.getCatalog().size());



    }
    @Test
    public void b_newElements() throws Exception {

        MvcResult result =    mockMvc.perform(post("/book/changeBook").accept("application/xml").contentType(MediaType.APPLICATION_XML).content("<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +
                "<book id=\"bk109\">\n" +
                "      <author>Corets, Eva</author>\n" +
                "      <title>Maeve Ascendant</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>45.95</price>\n" +
                "      <publish_date>2000-11-17</publish_date>\n" +
                "      <description>After the collapse of a nanotechnology \n" +
                "      society in England, the young survivors lay the \n" +
                "      foundation for a new society.</description>\n" +
                "   </book>\n" +
                "   <book id=\"bk110\">\n" +
                "      <author>Corets, Eva</author>\n" +
                "      <title>Maeve Ascendant</title>\n" +
                "      <genre>Fantasy</genre>\n" +
                "      <price>15.95</price>\n" +
                "      <publish_date>2000-11-17</publish_date>\n" +
                "      <description>After the collapse of a nanotechnology \n" +
                "      society in England, the young survivors lay the \n" +
                "      foundation for a new society.</description>\n" +
                "   </book>\n" +
                "</catalog>\n"))
                .andExpect(status().isOk()).andReturn();


        Catalog catalog = getCatalog(result);
        assertEquals("must be 2 elements",2, catalog.getCatalog().size());


    }

    private Catalog getCatalog(MvcResult result) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ByteArrayInputStream bis = new ByteArrayInputStream(result.getResponse().getContentAsByteArray());
        return (Catalog) jaxbUnmarshaller.unmarshal(bis);
    }

    @Test
    public void  c_testUpdate() throws Exception{

        MvcResult result =    mockMvc.perform(post("/book/changeBook").accept("application/xml").contentType(MediaType.APPLICATION_XML).content("<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +
                "<book id=\"bk109\">\n" +

                "      <price>4500.95</price>\n" +
                         "   </book>\n" +

                "</catalog>\n"))
                .andExpect(status().isOk()).andReturn();
        Catalog catalog = getCatalog(result);
        assertEquals("must be one elements",1, catalog.getCatalog().size());
     Book book = (Book) catalog.getCatalog().toArray()[0];
        assertEquals("update differ", "4500.95", book.getPrice());
        assertEquals("update ", "Fantasy", book.getGenre());


    }
    @Test
    public void d_checkCountselementafteroperation() throws Exception {
        MvcResult result =    mockMvc.perform(post("/book/changeBook").accept("application/xml").contentType(MediaType.APPLICATION_XML).content("<?xml version=\"1.0\"?>\n" +
                "<catalog>\n" +

                "</catalog>\n"))
                .andExpect(status().isOk()).andReturn();
        Catalog catalog = getCatalog(result);
        assertEquals("must be four elements",4, catalog.getCatalog().size());




    }

}
