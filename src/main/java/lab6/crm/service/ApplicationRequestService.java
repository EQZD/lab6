package lab6.crm.service;

import lab6.crm.entity.ApplicationRequest;
import lab6.crm.repository.ApplicationRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRequestService {

    private final ApplicationRequestRepository repository;

    public ApplicationRequestService(ApplicationRequestRepository repository) {
        this.repository = repository;
    }

    public List<ApplicationRequest> getAll() {
        return repository.findAll();
    }

    public ApplicationRequest getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApplicationRequest save(ApplicationRequest request) {
        return repository.save(request);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
