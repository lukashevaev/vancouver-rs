package com.ols.ruslan.neo;

import java.util.Map;
import java.util.regex.Pattern;

public class TypeDefiner {
    private String recordType;
    private final Map<RecordType, Pattern> patternsForType;
    private final VancouverInstance instance;

    public TypeDefiner(VancouverInstance instance){
        patternsForType = PatternFactory.getPatternsForType();
        this.instance = instance;
        recordType = instance.getRecordType().toLowerCase();
        defineType();
    }

    private void defineType() {
        String oldType = recordType;
        //patternsLookup
        for (Map.Entry<RecordType, Pattern> entry : patternsForType.entrySet()) {
            if (entry.getValue().matcher(oldType).find() || entry.getValue().matcher(instance.getTitle().toLowerCase()).find()) {
                recordType = entry.getKey().toString();
                break;
            }
        }

        if ("PROCEEDINGS".equals(recordType) && (!instance.getAuthor().equals("") || !instance.getTitle().equals(""))) {
            recordType = "INPROCEEDINGS";
            return;
        }

        if ("BOOK".equals(recordType)) {
            if (PatternFactory.pagesPattern.matcher(instance.getPages()).find()) recordType = "INBOOK";
        }

    }
    public String getRecordType(){
        return recordType;
    }
}