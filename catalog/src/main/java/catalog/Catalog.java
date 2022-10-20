package catalog;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Catalog {
   private List<CatalogItem> catalogItems = new ArrayList<>();

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }
    public void addItem(CatalogItem catalogItem) {
        if (catalogItem==null) {
            throw new IllegalArgumentException("Catalog item is empty");
        }
        catalogItems.add(catalogItem);
    }
    public void deleteItemByRegistrationNumber(String registrationNumber) {
        if (Validators.isBlank(registrationNumber)) {
            throw new IllegalArgumentException("Empty registration number");
        }
       List<CatalogItem> items = new ArrayList<>();
       for (CatalogItem actual: catalogItems) {
           if (actual.getRegistrationNumber().equals(registrationNumber)) {
               items.add(actual);
           }
       }
        catalogItems.removeAll(items);
    }
    public List<CatalogItem> getAudioLibraryItems() {
        List<CatalogItem> audioItems = new ArrayList<>();
        for (CatalogItem actual: catalogItems) {
            if (actual.hasAudioFeature()) {
                audioItems.add(actual);
            }
        }
        return audioItems;
    }
    public List<CatalogItem> getPrintedLibraryItems() {
        List<CatalogItem> printedItems = new ArrayList<>();
        for (CatalogItem actual: catalogItems) {
            if (actual.hasPrintedFeature()) {
               printedItems.add(actual);
            }
        }
        return printedItems;
    }
    public int getAllPageNumber() {
        int sum = 0;
        for (CatalogItem actual: catalogItems) {
            sum += actual.getNumberOfPagesAtOneItem();
        }
        return sum;
    }
    public double getAveragePageNumberMoreThan(int pageNumber) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("Page number must be positive");
        }
        double pages = 0;
        double books =0;
        int largestPageNumber = 0;
        for (CatalogItem actual: catalogItems) {
            int actualPages = actual.getNumberOfPagesAtOneItem();
            if (actualPages>pageNumber) {
                pages+= actualPages;
                books++;
            }
            if (actualPages>largestPageNumber) {
               largestPageNumber=  actualPages;
            }
        }
        tooHighPageNumber(pageNumber, largestPageNumber);
        return pages/books;
    }

    private void tooHighPageNumber(int pageNumber, int largestPageNumber) {
        if (pageNumber > largestPageNumber) {
            throw new IllegalArgumentException("No such book");
        }
    }

    public List<CatalogItem> findByCriteria(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            throw new IllegalArgumentException("Empty criteria");
        }
        List<CatalogItem> foundItems = new ArrayList<>();
        for (CatalogItem catalogItem : catalogItems) {
            boolean found = searchCriteria.hasTitle()
                    && catalogItem.getTitles().contains(searchCriteria.getTitle());
            if (searchCriteria.hasContributor()
                    && catalogItem.getContributors().contains(searchCriteria.getContributor())) {
                found = true;
            }
            if (found) {
                foundItems.add(catalogItem);
            }
        }
        return foundItems;
    }
    public void readBooksFromFile(Path path) {
       try(Scanner scanner = new Scanner(path)) {
           loadFile(scanner);

       }catch (IOException ioe) {
           throw new IllegalStateException("Can not read from file");
       }catch (IllegalArgumentException|IndexOutOfBoundsException ex) {
           throw new WrongFormatException("Line format in file is wrong",ex);
       }

    }

    private void loadFile(Scanner scanner) {
        while (scanner.hasNextLine()) {
               String[] parts = scanner.nextLine().split(";");
               List<String> authors = getAuthors(parts);
               String registrationNumber = "R-"+(catalogItems.size() + 1);
               int pieces = Integer.parseInt(parts[0]);
             LibraryItem book = new Book(parts[1],Integer.parseInt(parts[2]),authors);
               CatalogItem catalogItem = new CatalogItem(registrationNumber,pieces,book);
               catalogItems.add(catalogItem);
        }
    }

    private List<String> getAuthors(String[] parts) {
        List<String> authors = new ArrayList<>();
        authors.addAll(Arrays.asList(parts).subList(3, parts.length));
        return authors;
    }
}
