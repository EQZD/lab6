package lab6.crm.service;

import lab6.crm.entity.ApplicationRequest;
import lab6.crm.entity.Operators;
import lab6.crm.repository.ApplicationRequestRepository; // <-- Новый импорт
import lab6.crm.repository.OperatorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorsService {

    private final OperatorsRepository repository;
    private final ApplicationRequestRepository requestRepository; // <-- Внедряем репозиторий Заявок

    public OperatorsService(OperatorsRepository repository, ApplicationRequestRepository requestRepository) {
        this.repository = repository;
        this.requestRepository = requestRepository;
    }


    public List<Operators> getAll() {
        return repository.findAll();
    }

    public List<Operators> getAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public Operators getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Operators save(Operators operator) {
        return repository.save(operator);
    }

    public void delete(Long id) {
        Operators operatorToDelete = repository.findById(id).orElse(null);

        if (operatorToDelete != null) {

            List<ApplicationRequest> relatedRequests = operatorToDelete.getRequests();

            for (ApplicationRequest req : relatedRequests) {
                req.getOperators().remove(operatorToDelete);

                if (req.getOperators().isEmpty()) {
                    req.setHandled(false);
                }

                requestRepository.save(req);
            }

            repository.deleteById(id);
        }
    }
}