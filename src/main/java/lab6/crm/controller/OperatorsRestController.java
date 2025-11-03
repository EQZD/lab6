package lab6.crm.controller;

import lab6.crm.dto.ApplicationRequestDto;
import lab6.crm.dto.OperatorDto;
import lab6.crm.entity.ApplicationRequest;
import lab6.crm.entity.Operators;
import lab6.crm.service.ApplicationRequestService;
import lab6.crm.service.OperatorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operators")
public class OperatorsRestController {

    private final OperatorsService operatorsService;
    private final ApplicationRequestService requestService;

    public OperatorsRestController(OperatorsService operatorsService,
                                   ApplicationRequestService requestService) {
        this.operatorsService = operatorsService;
        this.requestService = requestService;
    }

    // GET /api/operators
    @GetMapping
    public List<OperatorDto> getAll() {
        return operatorsService.getAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // POST /api/operators
    @PostMapping
    public ResponseEntity<OperatorDto> create(@RequestBody OperatorDto dto) {
        Operators op = new Operators();
        op.setName(dto.getName());
        op.setSurname(dto.getSurname());
        op.setDepartment(dto.getDepartment());

        operatorsService.save(op);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(op));
    }

    // PUT /api/operators/{id}/assign/{requestId}
    @PutMapping("/{id}/assign/{requestId}")
    public ResponseEntity<ApplicationRequestDto> assignToRequest(@PathVariable Long id,
                                                                 @PathVariable Long requestId) {
        Operators operator = operatorsService.getById(id);
        if (operator == null) {
            return ResponseEntity.notFound().build();
        }

        ApplicationRequest request = requestService.getById(requestId);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        // добавить связь
        if (!request.getOperators().contains(operator)) {
            request.getOperators().add(operator);
        }
        if (!operator.getRequests().contains(request)) {
            operator.getRequests().add(request);
        }

        requestService.save(request); // owning side is ApplicationRequest

        // можно переиспользовать маппер из REST-контроллера заявок,
        // но чтобы не зависеть от него, мини-Mapper здесь:
        ApplicationRequestDto dto = new ApplicationRequestDto();
        dto.setId(request.getId());
        dto.setUserName(request.getUserName());
        dto.setCommentary(request.getCommentary());
        dto.setPhone(request.getPhone());
        dto.setHandled(request.isHandled());
        if (request.getCourse() != null) {
            dto.setCourseId(request.getCourse().getId());
        }
        dto.setOperatorIds(
                request.getOperators()
                        .stream()
                        .map(Operators::getId)
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(dto);
    }

    // ====== mapper ======
    private OperatorDto toDto(Operators op) {
        return new OperatorDto(
                op.getId(),
                op.getName(),
                op.getSurname(),
                op.getDepartment()
        );
    }
}
