package com.cielo.bootcamp.prospect.application.services;

import com.cielo.bootcamp.prospect.domain.Prospect;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ProspectQueueService {

    private final Queue<Prospect> queue = new LinkedList<>();

    public void add(Prospect prospect) {
        queue.add(prospect);
    }
    public Prospect retrieve() {
        return queue.poll();
    }

    public void removeQueue(Long prospectId) {
        List<Prospect> prospectFiltered = queue
                .stream()
                .filter(prospect -> prospect.getId().equals(prospectId))
                .toList();

        if (!prospectFiltered.isEmpty()) {
            queue.removeAll(prospectFiltered);
        }
    }

    public List<Prospect> getAll() {
        return new LinkedList<>(queue);
    }

    public int length() {
        return queue.size();
    }
}
