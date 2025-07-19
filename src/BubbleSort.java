public class BubbleSort {

    // Sắp xếp theo giá (price tăng dần)
    public static void sortByPrice(ArrayListADT<Book> books) {
        for (int i = 0; i < books.size() - 1; i++) {
            for (int j = 1; j < books.size() - i; j++) {
                if (books.get(j).getPrice() < books.get(j - 1).getPrice()) {
                    Book temp = books.get(j);
                    books.set(j, books.get(j - 1));
                    books.set(j - 1, temp);
                }
            }
        }
    }

    // Sắp xếp theo tựa sách (title tăng dần A-Z)
    public static void sortByTitle(ArrayListADT<Book> books) {
        for (int i = 0; i < books.size() - 1; i++) {
            for (int j = 1; j < books.size() - i; j++) {
                if (books.get(j).getTitle().compareToIgnoreCase(books.get(j - 1).getTitle()) < 0) {
                    Book temp = books.get(j);
                    books.set(j, books.get(j - 1));
                    books.set(j - 1, temp);
                }
            }
        }
    }

    // Sắp xếp theo tên tác giả (author tăng dần A-Z)
    public static void sortByAuthor(ArrayListADT<Book> books) {
        for (int i = 0; i < books.size() - 1; i++) {
            for (int j = 1; j < books.size() - i; j++) {
                if (books.get(j).getAuthor().compareToIgnoreCase(books.get(j - 1).getAuthor()) < 0) {
                    Book temp = books.get(j);
                    books.set(j, books.get(j - 1));
                    books.set(j - 1, temp);
                }
            }
        }
    }
}
