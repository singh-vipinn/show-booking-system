package com.vstech.service.serviceImpl;

import com.vstech.constant.AppConstants;
import com.vstech.model.Show;
import com.vstech.model.Slot;
import com.vstech.pojo.Genre;
import com.vstech.repository.ShowRepository;
import com.vstech.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository repo;

    @Override
    public void registerShow(String name, String genre) {
        repo.saveShow(new Show(name, Genre.valueOf(genre.toUpperCase())));
        System.out.println(name + AppConstants.SHOW_REGISTERED);
    }

    @Override
    public void onboardShowSlots(String name, List<String> slots) {
        for (String s : slots) {
            String[] parts = s.split(" ");
            String[] times = parts[0].split("-");
            int start = Integer.parseInt(times[0].split(":")[0]);
            int end = Integer.parseInt(times[1].split(":")[0]);
            int cap = Integer.parseInt(parts[1]);

            if (end - start != 1) {
                System.out.println(AppConstants.INVALID_DURATION);
                return;
            }
            repo.addSlot(name, new Slot(name, start, cap));
        }
        System.out.println(AppConstants.DONE);
    }

    @Override
    public void showAvailByGenre(String genre) {
        List<Slot> avail = new ArrayList<>();
        repo.getAllShows().stream()
                .filter(s -> s.getGenre().name().equalsIgnoreCase(genre))
                .forEach(s -> avail.addAll(repo.findSlotsByShow(s.getName())));

        // Ranking Strategy: Sort by start time
        avail.sort(Comparator.comparingInt(Slot::getStartTime));

        for (Slot sl : avail) {
            System.out.println(sl.getShowName() + ": (" + sl.getStartTime() + ":00-" + (sl.getStartTime()+1) + ":00) " + sl.getCapacity());
        }
    }
}

