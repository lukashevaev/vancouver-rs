package com.ols.ruslan.neo;


import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        return field.replaceAll("[^0-9-]", "");
    }

    private void refactorFields() throws IOException {
        instance.deleteRecordType();
        String author = instance.getAuthor();
        if (!author.equals("")) {
            String[] authors = author.split(",");
            StringBuilder builder = new StringBuilder();
            Arrays.stream(authors).limit(6).forEach(authorName -> builder.append(authorName).append(", "));
            instance.setAuthor(builder.toString().trim().substring(0, builder.length() - 2).concat(". "));
            if (authors.length >= 6) instance.setAuthor(instance.getAuthor() + " et al");
        }

        instance.setAddress(instance.getAddress() + ": ");
        instance.setConference(instance.getConference() + ": ");
        instance.setPages(getDigits(instance.getPages()));
        //instance.setPublisher(instance.getPublisher() + "; ");
        instance.setYear(instance.getYear() + "; ");
        instance.setVolume(instance.getVolume() + ": ");
        instance.setNumber(" (" + instance.getNumber() + ") ");
        instance.setEditor(instance.getEditor() + "; ");
        instance.setJournal(instance.getJournal() + ". ");

        if (PatternFactory.universityPattern.matcher(instance.getPublisher()).find())
            instance.setUniversity(instance.getPublisher());

        instance.getFields().entrySet().forEach(entry -> {
            String value = entry.getValue();
            if (value != null
                    && value.length() > 1
                    && !PatternFactory.specialSymbolsPattern.matcher(String.valueOf(value.charAt(value.length() - 1))).find()
                    && PatternFactory.notEmptyFieldPattern.matcher(entry.getValue()).find()
                    && PatternFactory.lastSymbolIsNotSpecial(entry.getValue())) {
                entry.setValue(entry.getValue() + ". ");
            }
        });

        //Удаляем пустые поля
        instance.setFields(
                instance.getFields()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null && !entry.getValue().equals("") && PatternFactory.notEmptyFieldPattern.matcher(entry.getValue()).find())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue , (a, b) -> a, LinkedHashMap::new)));
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
            instance.setVolume(instance.getVolume() + ": ");
            builder.append(instance.getJournal());
            builder.append(instance.getYear());
            builder.append(instance.getVolume());
            builder.append(instance.getNumber());
            builder.append(instance.getPages());
        } else if ("BOOK".equals(recordType)) {
            builder.append(instance.getEditor());
            builder.append(instance.getVolume());
            builder.append(instance.getNumber());
            builder.append(instance.getEdition());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("INBOOK".equals(recordType)) {
            instance.setPublisher("В: " + instance.getPublisher() + " (изд.)");
            builder.append(instance.getEditor());
            builder.append(instance.getTitleChapter());
            builder.append(instance.getVolume());
            builder.append(instance.getNumber());
            builder.append(instance.getEdition());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("THESIS".equals(recordType)) {
            if (!instance.getOldType().equals("")) builder.append(String.format("[%s]", instance.getOldType()));
            builder.append(instance.getUniversity());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
        } else if ("PROCEEDINGS".equals(recordType)) {
            builder.append(instance.getConference());
            builder.append(instance.getEditor());
            builder.append(instance.getTitleChapter());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("INPROCEEDINGS".equals(recordType)) {
            builder.append(instance.getConference());
            builder.append(instance.getEditor());
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
        String[] words = builder.toString().split(" ");
        String field = null;
        for (int i = words.length - 1; i >= 0; i--) {
            field = words[i];
            if (PatternFactory.notEmptyFieldPattern.matcher(field).find() && field.length() > 1) {
                break;
            }
        }
        String result = builder.toString();
        if (field != null) return builder
                .substring(0, result.lastIndexOf(field) + field.length())
                .replaceAll("\\.\\s*\\.", ".")
                .replaceAll("\\.[a-zA-Zа-яА-Я]?\\.", ".")
                .replaceAll(",\\s*[,.]", ".")
                .replaceAll(":\\s*[,.]", ":");
        return result;
    }
}
