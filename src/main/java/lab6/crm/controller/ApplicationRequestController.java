package lab6.crm.controller;

import lab6.crm.entity.*;
import lab6.crm.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/requests")
public class ApplicationRequestController {
    private final ApplicationRequestService requestService;
    private final CoursesService coursesService;
    private final OperatorsService operatorsService;

    public ApplicationRequestController(ApplicationRequestService requestService,
                                        CoursesService coursesService,
                                        OperatorsService operatorsService) {
        this.requestService = requestService;
        this.coursesService = coursesService;
        this.operatorsService = operatorsService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", requestService.getAll());
        return "requests/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        model.addAttribute("courses", coursesService.getAll());
        return "requests/add";
    }

    @PostMapping("/add")
    public String add(ApplicationRequest request) {
        requestService.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/{id}/assign")
    public String assignForm(@PathVariable Long id, Model model) {
        model.addAttribute("request", requestService.getById(id));
        model.addAttribute("operators", operatorsService.getAll());
        return "requests/assign";
    }

    @PostMapping("/{id}/assign")
    public String assign(@PathVariable Long id, @RequestParam List<Long> operatorIds) {
        ApplicationRequest req = requestService.getById(id);
        List<Operators> selected = operatorsService.getAllByIds(operatorIds);
        req.getOperators().addAll(selected); // теперь getOperators() точно есть
        req.setHandled(true);
        requestService.save(req);
        return "redirect:/requests";
    }


    @GetMapping("/{requestId}/operator/delete/{operatorId}")
    public String deleteOperatorFromRequest(@PathVariable Long requestId, @PathVariable Long operatorId) {
        ApplicationRequest request = requestService.getById(requestId);
        if (request != null) {
            request.getOperators().removeIf(operator -> operator.getId().equals(operatorId));
            if (request.getOperators().isEmpty()) {
                request.setHandled(false);
            }
            requestService.save(request);
        }
        return "redirect:/requests/" + requestId + "/assign";
    }

    @GetMapping("/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        return "redirect:/requests";
    }
}
