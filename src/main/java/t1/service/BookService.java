package t1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import t1.entity.Book;
import t1.entity.Catalog;
import t1.repository.Store;

import java.util.HashSet;

/**
 * Created by admin on 9/22/2016.
 */
@Component
public class BookService {

    @Autowired
    Store store;

    public BookService() {
    }

    public Catalog saveOrUpdate(Catalog catalog) {

        if ((catalog == null) || (catalog.getCatalog()==null)||(catalog.getCatalog().isEmpty()))
            return getCatalogWithAllEntities();

        Catalog result = new Catalog();
        HashSet set = new HashSet();
        result.setCatalog(set);
        for (Book book : catalog.getCatalog()) {
            set.add(store.addOrUpdateBook(book));

        }
     return result;
    }

    private Catalog getCatalogWithAllEntities() {

        HashSet<Book> set = new HashSet<Book>();
        set.addAll(store.getBooksCollection());
        Catalog ct = new Catalog();
        ct.setCatalog(set);
        return ct;

    }


}
