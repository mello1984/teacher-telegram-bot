package ru.butakov.teachertelegrambot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.teachertelegrambot.dao.ActivityRepository;
import ru.butakov.teachertelegrambot.domain.Activity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class ActivityServiceImpl implements ActivityService {
    ActivityRepository activityRepository;

    @Override
    public Activity findRandom() {
        var all = activityRepository.findAll();
        if (all.size() > 0) {
            int index = ThreadLocalRandom.current().nextInt(all.size());
            return all.get(index);
        } else return Activity.builder().quiz("No activity exists").build();
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> findById(long id) {
        return activityRepository.findById(id);
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }
}
