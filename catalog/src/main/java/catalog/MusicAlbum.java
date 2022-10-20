package catalog;


import java.util.ArrayList;
import java.util.List;

public class MusicAlbum implements LibraryItem{
    private String title;
    private int length;
    private List<String> composers;
    private List<String> performers;

    public MusicAlbum(String title, int length, List<String> composers, List<String> performers) {
        this.title = title;
        this.length = length;
        this.composers = composers;
        this.performers = performers;
    }

    public MusicAlbum(String title, int length, List<String> performers) {
        validate(title,performers,length);
        this.title = title;
        this.length = length;
        this.performers = performers;
    }

    public int getLength() {
        return length;
    }

    @Override
    public List<String> getContributors() {
        List<String>contributors = new ArrayList<>();
        if (composers!=null) {
            contributors.addAll(composers);
            contributors.addAll(performers);
        } else{
            contributors.addAll(performers);
        }

        return contributors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isPrinted() {
        return false;
    }

    @Override
    public boolean isAudio() {
        return true;
    }
    private void validate(String title, List<String> performers, int length) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Empty title");
        }
        if (Validators.isEmpty(performers)) {
            throw new IllegalArgumentException("No performer");
        }
        if (length<0) {
            throw new IllegalArgumentException("Illegal length: "+length);
        }

    }
}
