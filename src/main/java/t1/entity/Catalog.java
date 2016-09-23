package t1.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Created by admin on 9/22/2016.
 */
@XmlRootElement( name = "catalog" )
public class Catalog {

    public Set<Book> getCatalog() {
        return catalog;
    }

    @XmlElement( name = "book" )
    public void setCatalog(Set<Book> catalog) {
        this.catalog = catalog;
    }

    Set<Book> catalog;




}
