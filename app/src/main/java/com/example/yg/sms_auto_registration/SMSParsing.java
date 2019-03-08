package com.example.yg.sms_auto_registration;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SMSParsing {
    public ArrayList<String> extractDate(String _str) {
        ArrayList<String> list = new ArrayList<String>();
        String[] content = _str.split("\\s|\\r");

        Matcher startMatcher;
        Matcher dateMatcher;
        Matcher hagleMatcher;
        Matcher timeMatcher;
        Matcher engMatcher;

        if (_str.isEmpty()) {
            startMatcher = null;
            dateMatcher = null;
            hagleMatcher = null;
            timeMatcher = null;
            engMatcher = null;

        } else {
            String start = "\\[Web발신\\]";
            String date = "(0[1-9]|1[012])[- /.]*(0[1-9]|[12][0-9]|3[01])";
            String time = "(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])";
            String hangle = "^[가-힣]*$";
            String engle = "^[a-zA-Z]*$";

            int flag = Pattern.MULTILINE | Pattern.CASE_INSENSITIVE;

            Pattern startPattern = Pattern.compile(start);
            startMatcher = startPattern.matcher(_str);
            while (startMatcher.find()) {
                list.add(startMatcher.group(0));
            }

            Pattern datePattern = Pattern.compile(date);
            dateMatcher = datePattern.matcher(_str);
            while (dateMatcher.find()) {
                list.add(dateMatcher.group(0));
            }

            Pattern timePattern = Pattern.compile(time);
            timeMatcher = timePattern.matcher(_str);
            while (timeMatcher.find()) {
                list.add(timeMatcher.group(0));
            }

            for (int i = 0; i < content.length; i++) {
                Pattern engPattern = Pattern.compile(engle);
                hagleMatcher = engPattern.matcher(content[i]);
                while (hagleMatcher.find()) {
                    list.add(hagleMatcher.group(0));
                }
            }

            for (int i = 0; i < content.length; i++) {
                Pattern haglePattern = Pattern.compile(hangle);
                engMatcher = haglePattern.matcher(content[i]);
                while (engMatcher.find()) {
                    list.add(engMatcher.group(0));
                }
            }
        }

        return list;
    }
}