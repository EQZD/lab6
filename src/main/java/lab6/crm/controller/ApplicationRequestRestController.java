package lab6.crm.controller;

import lab6.crm.dto.ApplicationRequestDto;
import lab6.crm.entity.ApplicationRequest;
import lab6.crm.entity.Courses;
import lab6.crm.entity.Operators;
import lab6.crm.service.ApplicationRequestService;
import lab6.crm.service.CoursesService;
import lab6.crm.service.OperatorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
public class ApplicationRequestRestController {

    private final ApplicationRequestService requestService;
    private final CoursesService coursesService;
    private final OperatorsService operatorsService;

    public ApplicationRequestRestController(ApplicationRequestService requestService,
                                            CoursesService coursesService,
                                            OperatorsService operatorsService) {
        this.requestService = requestService;
        this.coursesService = coursesService;
        this.operatorsService = operatorsService;
    }

    // GET /api/requests
    @GetMapping
    public List<ApplicationRequestDto> getAll() {
        return requestService.getAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // GET /api/requests/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationRequestDto> getById(@PathVariable Long id) {
        ApplicationRequest req = requestService.getById(id);
        if (req == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(req));
    }

    // POST /api/requests
    @PostMapping
    public ResponseEntity<ApplicationRequestDto> create(@RequestBody ApplicationRequestDto dto) {
        Courses course = coursesService.getById(dto.getCourseId());
        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ApplicationRequest req = new ApplicationRequest();
        req.setUserName(dto.getUserName());
        req.setCommentary(dto.getCommentary());
        req.setPhone(dto.getPhone());
        req.setHandled(dto.isHandled());
        req.setCourse(course);

        // если хочешь назначать операторов сразу по operatorIds
        if (dto.getOperatorIds() != null && !dto.getOperatorIds().isEmpty()) {
            List<Operators> ops = operatorsService.getAllByIds(dto.getOperatorIds());
            req.setOperators(ops);
        }

        requestService.save(req); // предполагаем, что сервис либо void, либо возвращает ту же сущность
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(req));
    }

    // PUT /api/requests/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationRequestDto> update(@PathVariable Long id,
                                                        @RequestBody ApplicationRequestDto dto) {
        ApplicationRequest existing = requestService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (dto.getUserName() != null) existing.setUserName(dto.getUserName());
        if (dto.getCommentary() != null) existing.setCommentary(dto.getCommentary());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        existing.setHandled(dto.isHandled());

        if (dto.getCourseId() != null) {
            Courses course = coursesService.getById(dto.getCourseId());
            if (course == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            existing.setCourse(course);
        }

        requestService.save(existing);
        return ResponseEntity.ok(toDto(existing));
    }

    // DELETE /api/requests/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ====== mapper ======
    private ApplicationRequestDto toDto(ApplicationRequest req) {
        ApplicationRequestDto dto = new ApplicationRequestDto();
        dto.setId(req.getId());
        dto.setUserName(req.getUserName());
        dto.setCommentary(req.getCommentary());
        dto.setPhone(req.getPhone());
        dto.setHandled(req.isHandled());
        if (req.getCourse() != null) {
            dto.setCourseId(req.getCourse().getId());
        }
        if (req.getOperators() != null) {
            dto.setOperatorIds(
                    req.getOperators()
                            .stream()
                            .map(Operators::getId)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
