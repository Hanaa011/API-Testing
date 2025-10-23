package pojos;

import java.util.List;

public class BookAssignPojo {
    private String userId;
    private List<Isbn> collectionOfIsbns;

    public static class Isbn {
        private String isbn;
        public Isbn() {}
        public Isbn(String isbn) { this.isbn = isbn; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
    }

    public BookAssignPojo() {}

    public BookAssignPojo(String userId, List<Isbn> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<Isbn> getCollectionOfIsbns() { return collectionOfIsbns; }
    public void setCollectionOfIsbns(List<Isbn> collectionOfIsbns) { this.collectionOfIsbns = collectionOfIsbns; }
}
