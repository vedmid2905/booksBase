package t1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import t1.entity.Catalog;
import t1.service.BookService;

/**
 * Created by admin on 9/22/2016.
 */



@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/changeBook",
            method = RequestMethod.POST, produces  = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Catalog changeBook(@RequestBody Catalog catalog ){




        return bookService.saveOrUpdate(catalog);
    }


    @RequestMapping(value = "/changeBook",
            method = RequestMethod.GET, produces  = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Catalog test( ){

        return bookService.saveOrUpdate(null);
    }


}
