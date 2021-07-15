package ru.butakov.teachertelegrambot.service;

import ru.butakov.teachertelegrambot.domain.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    Activity findRandom();

    List<Activity> findAll();

    Optional<Activity> findById(long answerId);

    Activity save(Activity activity);
}
