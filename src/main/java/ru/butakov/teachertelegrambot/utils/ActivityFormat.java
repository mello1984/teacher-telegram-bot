package ru.butakov.teachertelegrambot.utils;

import ru.butakov.teachertelegrambot.domain.Activity;

public class ActivityFormat {
    public static String toQuiz(Activity activity) {
        return activity.getQuiz() + "\n" + "/" + activity.getId();
    }

    public static String toAnswer(Activity activity) {
        return activity.getAnswer();
    }
}
