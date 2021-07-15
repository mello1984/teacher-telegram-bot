package ru.butakov.teachertelegrambot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.butakov.teachertelegrambot.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
