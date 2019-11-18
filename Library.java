/**
 * CS 152 Lab 8 - Library
 * 2019/4/14
 * Ilham Babajanov
 */

public class Library {

    /**
     * Unique books in the library.
     */
    private Book[] books;

    /**
     * Number of copies for each book.
     */
    private int[] copies;

    /**
     * Number of copies currently checked out for each book.
     */
    private int[] checkedOut;

    /**
     * Number of unique books in the library.
     */
    private int numBooks;


    /**
     * Construct a new empty Library.
     */
    public Library(int librarySize) {
        books = new Book[librarySize];
        copies = new int[librarySize];
        checkedOut = new int[librarySize];
        numBooks = 0;
    }


    /**
     * Get the number of total copies of all books that exist in the
     * library.
     * @return Total number of copies in the library.
     */
    public int getTotalCopies() {
        int countTotalCopies =0;
        for (int i=0; i<numBooks; i++) {
            countTotalCopies = countTotalCopies + copies[i];
        }
        return countTotalCopies;
    }


    /**
     * Get the number of copies currently checked out.
     * @return Total number of copies checked out.
     */
    public int getNumCheckedOut() {
        int countCheckedOut =0;
        for (int i=0; i<numBooks; i++) {
            countCheckedOut = countCheckedOut + checkedOut[i];
        }
        return countCheckedOut;
    }


    /**
     * Get a String representing the status of the library.
     * @return Status string.
     */
    public String getStatus() {

        return ("Total unique books: " + this.numBooks + "\n" +
                "Total number of copies: " + getTotalCopies() + "\n" +
                "Total checked out: " + getNumCheckedOut());
    }


    /**
     * Add all the books in the array to the library. Adds one copy of
     * each book.
     * @param newBooks Books to add.
     */
    public void addBooks(Book[] newBooks) {

        for (Book b: newBooks) {
            addBook(b);
        }
    }


    /**
     * Add a single book the library.
     * If the book is already present, adds another copy.
     * If the book is new, add it after the existing books.
     * @param b Book to add.
     */
    public void addBook(Book b) {

        boolean checker = false;

        for (int i=0; i<numBooks; i++) {
            if (books[i].equals(b)) {
                copies[i]++;
                checker = true;
                break;
            }
        }

        if (checker) {

        } else {
            if (numBooks >= books.length) {
                expandCapacity();
                books[numBooks] = b;
                copies[numBooks] = 1;
                numBooks++;
            } else {
                books[numBooks] = b;
                copies[numBooks] = 1;
                numBooks++;
            }
        }
    }


    /**
     * Helper method.
     * Expands the books array if numBooks >= books.length.
     */
    public void expandCapacity() {
        Book[] otherBooks = new Book[books.length * 2];
        int[] otherCopies = new int[copies.length * 2];
        for (int i=0; i<numBooks; i++) {
            otherBooks[i] = books[i];
            otherCopies[i] = copies[i];
        }
        books = otherBooks;
        copies = otherCopies;
    }


    /**
     * Checks out a book from the library if possible.
     * @param b Book to check out.
     * @return String denoting success or failure.
     */
    public String checkOut(Book b) {

        String result = "Book not found.";
        for (int i = 0; i < numBooks; i++) {
            if (b.equals(books[i])) {
                if (checkedOut[i] == copies[i]) {
                    result = "All out of copies.";
                    break;
                }
                if (checkedOut[i] < copies[i]) {
                    checkedOut[i]++;
                    result = "Checked out!";
                    break;
                }
            }
        }
        return result;
    }


    /**
     * Checks in a book to the library if possible.
     * @param b Book to check in.
     * @return String denoting success or failure.
     */
    public String checkIn(Book b) {

        String result = "Book not found.";
        for (int i=0; i<numBooks; i++) {
            if (b.equals(books[i])) {
                if (checkedOut[i] == 0) {
                    result = "All of our copies are already checked in.";
                    break;
                }
                if (checkedOut[i] > 0) {
                    checkedOut[i]--;
                    result = "Checked in!";
                    break;
                }
            }
        }
        return result;
    }


    /**
     * Get string representation of entire library collection and status.
     * @return String representation of library.
     */
    public String toString() {

        String result = "";
        for (int i=0; i<numBooks; i++) {
            result = result + (i + ". " + books[i] + " : " +
                    (copies[i]-checkedOut[i]) + "/" + copies[i] + "\n");
        }
        result = result + "\n" + getStatus();

        return result;
    }


    /**
     * Get number of unique books that exist for a given author.
     * @param a The author to check.
     * @return Number of books by the author.
     */
    public int numBooksByAuthor(Author a) {

        int count = 0;
        for (int i=0; i<numBooks; i++) {
            if (a.hasSameName(books[i].getAuthor())) {
                count++;
            }
        }
        return count;
    }


    /**
     * Returns a String that lists the unique books which exist for a
     * given author, in standard book format, with a newline after
     * each.  If no books are found for the author, returns string
     * that says so.
     *
     * @param a The author in question.
     * @return String listing books by the author.
     */
    public String listBooksByAuthor(Author a) {

        String result = "";
        boolean check = true;
        for (int i=0; i<numBooks; i++) {
            if (a.hasSameName(books[i].getAuthor())) {
                result = result + (books[i]) + "\n";
                check = false;
            }
        }
        if (check) return ("No books by " + a + ".");

        return result;
    }


    /**
     * Returns string that lists the unique books which contain the
     * given string within their titles, without regard for case, with
     * a newline after each.  If no books are found containing the
     * string, returns string that says so.
     *
     * @param s The string to look for in the titles.
     * @return String listing books that contain given string in titles.
     */
    public String listBooksByTitle(String s) {

        String str = s.toLowerCase();
        String result = "";
        boolean check = true;

        for (int i=0; i<numBooks; i++) {
            String title = books[i].getTitle().toLowerCase();

            if (title.contains(str)) {
                result = result + books[i] + "\n";
                check = false;
            }
        }
        if (check) return ("No books with \"" + s +"\" in the title.");

        return result;
    }


    /**
     * Deletes book entirely from the library.
     *
     * @param b Book to remove.
     * @return String denoting success or failure.
     */
    public String deleteBook(Book b) {

        for (int i=0; i<numBooks; i++) {
            if (books[i].equals(b)) {
                removeBook(i);
                return ("Book removed.");
            }
        }
        return ("Book not found.");
    }


    /**
     * Helper method.
     * Takes integer number as @param.
     * When called, deletes the book at @param index and updates all the arrays
     * not to leave a hole in the sequence.
     * @param index of the book to be deleted.
     */
    private void removeBook(int index) {
        for (int i=index; i<numBooks - 1; i++) {
            books[i] = books[i+1];
            copies[i] = copies[i+1];
            checkedOut[i] = checkedOut[i+1];
        }
        numBooks--;
        books[numBooks] = null;
        copies[numBooks] = 0;
        checkedOut[numBooks] = 0;
    }


}
