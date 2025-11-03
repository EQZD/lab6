package lab6.crm.controller;

import lab6.crm.entity.Courses;
import lab6.crm.service.CoursesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", coursesService.getAll());
        return "courses/list";
    }

    @GetMapping("/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Courses());
        return "courses/add";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("course") Courses course) {
        coursesService.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        coursesService.delete(id);
        return "redirect:/courses";
    }
}
