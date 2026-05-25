package com.example.demo.controller;

import com.example.demo.model.Schedule;
import com.example.demo.service.ScheduleService;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "http://localhost:3000")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable @NonNull Long id) {

        return scheduleService.getScheduleById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody @NonNull Schedule schedule) {

        return scheduleService.saveSchedule(schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(
            @PathVariable @NonNull Long id,
            @RequestBody @NonNull Schedule schedule) {

        schedule.setScheduleId(id);

        return scheduleService.saveSchedule(schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable @NonNull Long id) {

        scheduleService.deleteSchedule(id);
    }
}