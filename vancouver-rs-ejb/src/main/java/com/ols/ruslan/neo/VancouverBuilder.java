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
        String author = instance.getAuthor();
        if (!author.equals("")) {
            String[] authors = author.split("-");
            StringBuilder builder = new StringBuilder();
            Arrays.stream(authors).limit(6).forEach(authorName -> builder.append(authorName).append(", "));
            instance.setAuthor(builder.substring(0, builder.length() - 3));
            if (authors.length >= 6) instance.setAuthor(instance.getAuthor() + " et al");
        }



        if (PatternFactory.universityPattern.matcher(instance.getPublisher()).find())
            instance.setUniversity(instance.getPublisher());
    }

    public String buildVancouver() {
        StringBuilder builder = new StringBuilder();
        Map<String, String> fields = instance.getFields();
        fields.entrySet().forEach(entry -> entry.setValue(entry.getValue() + ", "));
        if ("INPROCEEDINGS".equals(recordType)
                || "ARTICLE".equals(recordType)
                || "PHDTHESIS".equals(recordType)
                || "MASTERSTHESIS".equals(recordType)
                || "INBOOK".equals(recordType)
        ) instance.setTitle("\"" + instance.getTitle() + "\"");
        builder.append(instance.getAuthor())
                .append(instance.getYear())
                .append(instance.getTitle());
        if ("ARTICLE".equals(recordType)) {
            builder.append(instance.getJournal());
            builder.append(instance.getVolume());
            builder.append(instance.getPages());
        } else if ("BOOK".equals(recordType)) {
            builder.append(instance.getPublisher());
            builder.append(instance.getAddress());
        } else if ("INBOOK".equals(recordType)) {
            instance.setTitleChapter("в " + instance.getTitleChapter());
            builder.append(instance.getTitleChapter());
            builder.append(instance.getPublisher());
            builder.append(instance.getAddress());
            builder.append(instance.getPages());
        }
        else if ("PHDTHESIS".equals(recordType)) {
            builder.append("Abstract of bachelor dissertation");
            builder.append(instance.getUniversity());
            builder.append(instance.getAddress());
        } else if ("MASTERSTHESIS".equals(recordType)) {
            builder.append("Abstract of master dissertation");
            builder.append(instance.getUniversity());
            builder.append(instance.getAddress());
        } else if ("PROCEEDINGS".equals(recordType)) {
            builder.append(instance.getConference());
            builder.append(instance.getAddress());
            builder.append(instance.getData());
            builder.append(instance.getPages());
        }
        builder.trimToSize();
        builder.deleteCharAt(builder.length() - 2);
        return builder.toString().replace(",,", ",");
    }
}
