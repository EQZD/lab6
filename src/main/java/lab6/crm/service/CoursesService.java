package lab6.crm.service;

import lab6.crm.entity.ApplicationRequest;
import lab6.crm.entity.Courses;
import lab6.crm.repository.ApplicationRequestRepository;
import lab6.crm.repository.CoursesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {

    private final CoursesRepository repository;
    private final ApplicationRequestRepository requestRepository;

    public CoursesService(CoursesRepository repository, ApplicationRequestRepository requestRepository) {
        this.repository = repository;
        this.requestRepository = requestRepository;
    }

    public List<Courses> getAll() {
        return repository.findAll();
    }

    public Courses getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Courses save(Courses course) {
        return repository.save(course);
    }

    public void delete(Long id) {
        List<ApplicationRequest> requestsToDetach = requestRepository.findAllByCourseId(id);

        for (ApplicationRequest req : requestsToDetach) {
            req.setCourse(null);
            requestRepository.save(req);
        }

        repository.deleteById(id);
    }
}