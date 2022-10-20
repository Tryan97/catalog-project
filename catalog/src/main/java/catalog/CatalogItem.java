package catalog;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogItem {
    private String registrationNumber;
    private int pieces;
    private List<LibraryItem> libraryItems;

    public CatalogItem(String registrationNumber, int pieces, LibraryItem... libraryItems) {
        validate(registrationNumber,pieces);
        this.registrationNumber = registrationNumber;
        this.pieces = pieces;
        this.libraryItems = Arrays.asList(libraryItems);
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getPieces() {
        return pieces;
    }

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }
    public boolean hasAudioFeature() {
        for (LibraryItem actual: libraryItems) {
            if (actual.isAudio()) {
                return true;
            }
        }
        return false;
    }
    public boolean hasPrintedFeature() {
        for (LibraryItem actual: libraryItems) {
            if (actual.isPrinted()) {
                return true;
            }
        }
        return false;
    }
    public int getNumberOfPagesAtOneItem() {
        int sum = 0;
        for (LibraryItem actual: libraryItems) {
            if (actual.isPrinted()) {
                sum+=((Book) actual).getNumberOfPages();
            }
        }
        return sum;
    }
    public List<String> getContributors() {
        List<String> contributors = new ArrayList<>();
        for (LibraryItem actual: libraryItems) {
           checkIfContributorAlreadyInList(contributors,actual.getContributors());
        }
        return contributors;
    }
    public List<String>getTitles() {
        List<String> titles = new ArrayList<>();
        for (LibraryItem actual: libraryItems) {
            titles.add(actual.getTitle());
        }
        return titles;
    }
    private void validate(String registrationNumber, int pieces) {
        if (Validators.isBlank(registrationNumber)) {
            throw new IllegalArgumentException("Empty registration number");
        }
        if (pieces<0) {
            throw new IllegalArgumentException("Pieces must be at least 0");
        }
    }
    private List<String> checkIfContributorAlreadyInList(List<String> contributorsSum,List<String> contributors){
        for (String actual: contributors) {
            if (!contributorsSum.contains(actual)) {
                contributorsSum.add(actual);
            }
        }
        return contributorsSum;
    }

}
