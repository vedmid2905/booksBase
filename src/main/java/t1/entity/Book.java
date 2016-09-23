package t1.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by admin on 9/21/2016.
 */
@XmlType( propOrder = { "author", "title", "genre" ,
                       "price", "publishDate","description"} )

@XmlRootElement( name = "book" )
public class Book {

    private String id;
    private String author;
    private String title;
    private String genre;
    private String price;
    private String publishDate;
    private String description;

    public Book(){}

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", price='" + price + '\'' +
                ", publish_date='" + publishDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    //getter and setter
    public String getId() {
        return id;
    }
    @XmlAttribute(name="id")
    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }


    public String getPublishDate() {
        return publishDate;
    }
    @XmlElement(name  ="publish_date" )
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return !(id != null ? !id.equals(book.id) : book.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
