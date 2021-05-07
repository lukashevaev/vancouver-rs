package com.ols.ruslan.neo;


import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class VancouverBuilder {
    private final String recordType;
    private final VancouverInstance instance;


    public VancouverBuilder(Map<String, String> fields) {
        instance = new VancouverInstance(fields);
        TypeDefiner typeDefiner = new TypeDefiner(instance);
        this.recordType = typeDefiner.getRecordType();
        try {
            refactorFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для выделения цифр из поля
    public String getDigits(String field) {
        return field.replaceAll("[^0-9]", "");
    }

    private void refactorFields() throws IOException {
        instance.deleteRecordType();
        String author = instance.getAuthor();
        if (!author.equals("")) {
            String[] authors = author.split("-");
            StringBuilder builder = new StringBuilder();
            Arrays.stream(authors).limit(6).forEach(authorName -> builder.append(authorName).append(", "));
            instance.setAuthor(builder.substring(0, builder.length() - 3));
            if (authors.length >= 6) instance.setAuthor(instance.getAuthor() + " et al");
        }

        if (!"".equals(instance.getAddress())) instance.setAddress(instance.getAddress() + ": ");
        if (!"".equals(instance.getConference())) instance.setConference(instance.getConference() + ": ");
        if (!"".equals(instance.getPages())) instance.setPages("с. " + getDigits(instance.getPages()));
        if (!"".equals(instance.getPublisher())) instance.setPublisher(instance.getPublisher() + ";");
        if (!"".equals(instance.getYear())) instance.setYear(instance.getYear() + ".");



        if (PatternFactory.universityPattern.matcher(instance.getPublisher()).find())
            instance.setUniversity(instance.getPublisher());

        instance.getFields().entrySet().forEach(entry -> {
            if (!PatternFactory.specialSymbolsPattern.matcher(entry.getValue()).find()) {
                entry.setValue(entry.getValue() + ". ");
            }
        });
    }

    public String buildVancouver() {
        StringBuilder builder = new StringBuilder();
        if (!"".equals(instance.getAuthor())) {
            builder.append(instance.getAuthor())
                    .append(instance.getTitle());
        } else {
            builder.append(instance.getTitle());
        }
        if ("ARTICLE".equals(recordType)) {
            builder.append(instance.getJournal());
            builder.append(instance.getYear());
            if (!instance.getVolume().equals("")) builder.append(instance.getVolume()).append(": ");
            builder.append(instance.getPages());
        } else if ("BOOK".equals(recordType)) {
            builder.append(instance.getVolume());
            builder.append(instance.getEdition());
            builder.append(instance.getPublisher());
            builder.append(instance.getAddress());
                    //getEditor;
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("INBOOK".equals(recordType)) {
            if (!instance.getPublisher().equals("")) instance.setPublisher("В: " + instance.getPublisher() + "(изд.)");
            builder.append(instance.getPublisher());
            builder.append(instance.getTitleChapter());
            builder.append(instance.getVolume());
            builder.append(instance.getEdition());
            builder.append(instance.getAddress());
            //getEditor;
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("THESIS".equals(recordType)) {
            if (!instance.getOldType().equals("")) builder.append("[").append(instance.getOldType()).append("]");
            builder.append(instance.getUniversity());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
        } else if ("PROCEEDINGS".equals(recordType)) {
            builder.append(instance.getConference());
            //getEditor;
            builder.append(instance.getTitleChapter());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("INPROCEEDINGS".equals(recordType)) {
            builder.append(instance.getConference());
            //getEditor;
            builder.append(instance.getTitleChapter());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else {
            builder = new StringBuilder();
            instance.getFields().values().forEach(builder::append);
        }
        builder.trimToSize();
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString().replace("..", ".").replace(",,", ",");
    }
}
