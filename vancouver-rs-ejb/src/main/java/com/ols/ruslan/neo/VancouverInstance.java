package com.ols.ruslan.neo;

import java.util.Locale;
import java.util.Map;

public class VancouverInstance {
    private Map<String, String> fields;
    private String oldType;

    public VancouverInstance(Map<String, String> fields) {
        this.fields = fields;
        oldType = getRecordType();
        setTitle(getTitle());
        setJournal(getJournal());
        fields.remove("journal_description");
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getRecordType() {
        return fields.get("recordType") != null ? fields.get("recordType") : "";
    }

    public void setRecordType(String recordType) {
        try {
            if (fields.get("recordType") == null) throw new NullPointerException();
            this.fields.put("recordType", recordType);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getTechreport() {
        return fields.get("techreport");
    }

    public void setTechreport(String techreport) {
        try {
            if (fields.get("techreport") == null) throw new NullPointerException();
            this.fields.put("techreport", techreport);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getConference() {
        return fields.get("conference") != null ? fields.get("conference") : "";
    }

    public void setConference(String conference) {
        try {
            if (fields.get("conference") == null) throw new NullPointerException();
            this.fields.put("conference", conference);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    public String getUniversity() {
        return fields.get("university") != null ? fields.get("university") : "";
    }

    public void setUniversity(String university) {
        try {
            if (fields.get("university") == null) throw new NullPointerException();
            this.fields.put("university", university);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



    public String getAuthor() {
        return fields.get("author") != null ? fields.get("author") : "";
    }

    public void setAuthor(String author) throws NullPointerException{
        try {
            if (fields.get("author") == null) throw new NullPointerException();
            this.fields.put("author", author);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getYear() {
        return fields.get("year") != null ? fields.get("year") : "";
    }

    public void setYear(String year) {
        try {
            if (fields.get("year") == null) throw new NullPointerException();
            this.fields.put("year", year);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getPublisher() {
        return fields.get("publisher") != null ? fields.get("publisher") : "";
    }

    public void setPublisher(String publisher) {
        try {
            if (fields.get("publisher") == null) throw new NullPointerException();
            this.fields.put("publisher", publisher);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        StringBuilder builder = new StringBuilder();
        String recordType = fields.get("recordType");
        if (fields.get("title") != null) builder.append(fields.get("title"));
        if (recordType != null && PatternFactory.notEmptyFieldPattern.matcher(recordType).find()) builder.append(": ").append(recordType);
        return builder.toString();
    }

    public void setTitle(String title) {
        try {
            if (fields.get("title") == null) throw new NullPointerException();
            this.fields.put("title", title);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getLanguage() {
        return fields.get("language") != null ? fields.get("language") : "";
    }

    public void setLanguage(String language) {
        try {
            if (fields.get("language") == null) throw new NullPointerException();
            this.fields.put("language", language);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getSchool() {
        return fields.get("school") != null ? fields.get("school") : "";
    }

    public void setSchool(String school) {
        try {
            if (fields.get("school") == null) throw new NullPointerException();
            this.fields.put("school", school);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return fields.get("url") != null ? fields.get("url") : "";
    }

    public void setUrl(String url) {
        try {
            if (fields.get("url") == null) throw new NullPointerException();
            this.fields.put("url", url);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return fields.get("address") != null ? fields.get("address") : "";
    }

    public void setAddress(String address) {
        try {
            if (fields.get("address") == null) throw new NullPointerException();
            this.fields.put("address", address);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getEdition() {
        return fields.get("edition") != null ? fields.get("edition") : "";
    }

    public void setEdition(String edition) {
        try {
            if (fields.get("edition") == null) throw new NullPointerException();
            this.fields.put("edition", edition);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getEditor() { return fields.get("editor") != null ? fields.get("editor") : ""; }

    public void setEditor(String editor) {
        try {
            if (fields.get("editor") == null) throw new NullPointerException();
            this.fields.put("editor", editor);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getJournal() {
        StringBuilder journal = new StringBuilder();
        if (fields.get("journal") != null && !fields.get("journal").equals("")) journal.append(fields.get("journal"));
        if (fields.get("journal_description") != null && PatternFactory.journalPattern.matcher(fields.get("journal_description").toLowerCase()).find()) {
            journal.append(", ").append(fields.get("journal_description"));
            setRecordType("ARTICLE");
        }
        return journal.toString();
    }

    public void setJournal(String journal) {
        try {
            if (fields.get("journal") == null) throw new NullPointerException();
            this.fields.put("journal", journal);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getNumber() {
        return fields.get("number") != null ? fields.get("number") : "";
    }

    public void setNumber(String number) {
        try {
            if (fields.get("number") == null) throw new NullPointerException();
            this.fields.put("number", number);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getPages() {
        return fields.get("pages") != null ? fields.get("pages") : "";
    }

    public void setPages(String pages) {
        try {
            if (fields.get("pages") == null) throw new NullPointerException();
            this.fields.put("pages", pages);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getVolume() {
        return fields.get("volume") != null ? fields.get("volume") : "";
    }

    public void setVolume(String volume) {
        try {
            if (fields.get("volume") == null) throw new NullPointerException();
            this.fields.put("volume", volume);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void deleteYear() {
        this.fields.remove("year");
    }

    public void deletePublisher() {
        this.fields.remove("publisher");
    }

    public void deleteTitle() {
        this.fields.remove("title");
    }

    public void deleteLanguage() {
        this.fields.remove("language");
    }

    public void deleteSchool() {
        this.fields.remove("school");
    }

    public void deleteUrl() {
        this.fields.remove("url");
    }

    public void deleteAddress() {
        this.fields.remove("address");
    }

    public void deleteEdition() {
        this.fields.remove("edition");
    }

    public void deleteJournal() {
        this.fields.remove("journal");
    }

    public void deleteNumber() {
        this.fields.remove("number");
    }

    public void deletePages() {
        this.fields.remove("pages");
    }

    public void deleteVolume() {
        this.fields.remove("volume");
    }

    public void deleteAuthor() {
        this.fields.remove("author");
    }

    public void deleteRecordType() {
        this.fields.remove("recordType");
    }

    public void deleteTechreport() {
        this.fields.remove("techreport");
    }

    public String getTitleChapter() {
        return fields.get("title_chapter") != null ? fields.get("title_chapter") : "";
    }

    public void setTitleChapter(String title_chapter) {
        this.fields.put("title_chapter", title_chapter);
    }





}

