package com.example.demo.service;

import com.example.demo.model.Schedule;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById( @NonNull Long id) {
        return scheduleRepository.findById(id);
    }

    public Schedule saveSchedule( @NonNull Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(@NonNull Long id) {
        scheduleRepository.deleteById(id);
    }
}