package com.ols.ruslan.neo;

import org.jsoup.helper.StringUtil;

import javax.swing.text.StringContent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Данный класс используется для создания паттернов для того,
 * чтобы найти тип записи, если он указан явно, а также для того,
 * чтобы найти случаи нетипичной записи полей.
 */


public class PatternFactory {

    private final Map<String, String> patternForTags = new LinkedHashMap<String, String>();
    private static final Map<RecordType, Pattern> patternsForType = new HashMap<>();

    public PatternFactory() {
        patternsForType.put(RecordType.BOOK,
                Pattern.compile("энциклопедия|encyclopa[e]?dia|сборник|собрание|сочинения|работы|книга|словарь|" +
                        "(в|in)\\s\\d+-?х?\\s(т|ч|vols)\\.?$")); // Пример: сборник в 3 томах
        patternsForType.put(RecordType.PROCEEDINGS,
                Pattern.compile("proceedings|" +
                        "of\\s*(a|the)\\s*conference|" +
                        "conference|" +
                        "proceedings\\s*of|" +
                        "of\\s*(a|the).*\\s*colloquium|" +
                        "of\\s*symposia|" +
                        "symposium|" +
                        "of\\s*(a|the)\\s*congress"));
        patternsForType.put(RecordType.INPROCEEDINGS,
                Pattern.compile("inproceedings"));
        patternsForType.put(RecordType.ARTICLE,
                Pattern.compile("журнал|" +
                        "journal|" +
                        "статья|" +
                        "article"));
        patternsForType.put(RecordType.ABSTRACT,
                Pattern.compile("abstract\\s*of|автореферат"));
        patternsForType.put(RecordType.THESIS,
                Pattern.compile("дис.*канд|выпускная квалификационная работа|дис.*маг|дис.*док|диссертац|" +
                        "((master(s)|bachelor)?)?\\s*thesis\\s*((of)?\\smaster(s)|bachelor)?"));
        patternsForType.put(RecordType.PATENT,
                Pattern.compile("patent"));
        patternsForType.put(RecordType.ETHER,
                Pattern.compile("ether"));
    }

    /** Для поля "pages"
     * Если поле совпадает с паттерном "digits-digits"
     * Например "10-20", "345-466"
     */
    public static final Pattern pagesPattern = Pattern.compile("\\D*\\d*-\\d*");

    /**
     * Для поля "volume"
     * Если поле совпадает с паттерном : "chapter 3", "#5", "№ 9", "том 8", "vol № 12"
     * Проверка, что поле является томом или главой
     */
    public static final Pattern volumePattern = Pattern.compile("^((том|vol|chapter|[nтpч№#]|part|часть)\\.?\\s*[нn№#]?\\s*\\d*)");

    /**
     * Для поля "number"
     * Если поле совпадает с паттерном : "N. 15", "number 8", "№ 9"
     * Проверка, что поле является номером журнала
     */
    public static final Pattern numberPattern = Pattern.compile("^(([#№n]|number)\\.?\\s*\\d*)");

    /** Для поля "pages"
     * Если поле совпадает с паттерном "digits"
     * Например "10 стр", "345 pages"
     */
    public static final Pattern pagePattern = Pattern.compile("\\d*\\s*(pages|[pсc]|стр|страниц)\\.?");

    public static final Pattern spbPattern = Pattern.compile("s[.-]?pb");

    public static final Pattern universityPattern = Pattern.compile("university");

    public Map<String, String> getPatternForTags() {
        return patternForTags;
    }

    public Map<RecordType, Pattern> getPatternsForType() {
        return patternsForType;
    }

    public static Pattern authorPattern = Pattern.compile("");

    public static Pattern russianPattern = Pattern.compile(".*[а-яА-Я].*");

    public static final Pattern journalPattern = Pattern.compile("журнал|journal");

    public static final Pattern specialSymbolsPattern = Pattern.compile("[:;.,\\-/\\s]");

    public static final Pattern notEmptyFieldPattern = Pattern.compile("[a-zA-Zа-яА-Я0-9]{2,}");

    public static boolean lastSymbolIsNotSpecial(String field) {
        return !PatternFactory.specialSymbolsPattern.matcher(String.valueOf(field.charAt(field.length() - 1))).find();
    }
}
