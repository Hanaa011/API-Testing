package pojos;

import java.util.List;

public class BookResponsePojo {
    private String userId;
    private List<BookAssignPojo.Isbn> books;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<BookAssignPojo.Isbn> getBooks() { return books; }
    public void setBooks(List<BookAssignPojo.Isbn> books) { this.books = books; }
}
