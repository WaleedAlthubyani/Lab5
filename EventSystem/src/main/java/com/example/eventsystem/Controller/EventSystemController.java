package com.example.eventsystem.Controller;

import com.example.eventsystem.ApiResponse.ApiResponse;
import com.example.eventsystem.Model.Event;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/event-system")
public class EventSystemController {
    ArrayList<Event> events=new ArrayList<>();

    @GetMapping("/events")
    public ArrayList<Event> displayEvents() {
        return events;
    }

    @PostMapping("/create-new-event")
    public ApiResponse createEvent(@RequestBody Event event) {
        for (Event value : events) {
            if (value.getID().equals(event.getID())) {
                return new ApiResponse("An event with this ID already exists");
            }
        }
        if (event.getID().isEmpty()) {
            return new ApiResponse("Event ID cannot be empty");
        }
        if (event.getStartDate().isAfter(event.getEndDate())) {
            return new ApiResponse("Start date cannot be after end date");
        }
        if (event.getCapacity()<0){
            return new ApiResponse("Capacity cannot be negative");
        }

        events.add(event);
        return new ApiResponse("Event created successfully");
    }

    @PutMapping("/update-event/{id}")
    public ApiResponse updateEvent(@RequestBody Event event,@PathVariable String id) {
        if (!event.getID().equals(id)) {
            for (Event e : events) {
                if (e.getID().equals(event.getID())) {
                    return new ApiResponse("An event with this ID already exists");
                }
            }
        }

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getID().equals(id)) {
                events.set(i, event);
                return new ApiResponse("Event updated successfully");
            }
        }
        return new ApiResponse("Event not found");
    }

    @DeleteMapping("delete-event/{id}")
    public ApiResponse deleteEvent(@PathVariable String id) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getID().equals(id)) {
                events.remove(i);
                return new ApiResponse("Event deleted successfully");
            }
        }
        return new ApiResponse("Event not found");
    }

    @PutMapping("/change-capacity/{id}/{capacity}")
    public ApiResponse changeCapacity(@PathVariable String id,@PathVariable int capacity) {
        if (capacity < 0) {
            return new ApiResponse("Capacity must be a positive number or zero");
        }

        for (Event event : events) {
            if (event.getID().equals(id)) {
                event.setCapacity(capacity);
                return new ApiResponse("Capacity changed successfully");
            }
        }

        return new ApiResponse("Event not found");
    }

    @GetMapping("/get-event-by-id/{id}")
    public Event getEventById(@PathVariable String id) {
        for (Event event : events) {
            if (event.getID().equals(id)) {
                return event;
            }
        }
        return null;
    }
}
