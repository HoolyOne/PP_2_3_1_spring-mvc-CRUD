package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "user-list";
    }
    @GetMapping("/add")
    public String showAddForm() {
        return "user-add";
    }
    @PostMapping("/add")
    public String addUser(@RequestParam String name,
                          @RequestParam String surname,
                          @RequestParam Integer age) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        userService.addUser(user);
        return "redirect:/users";
    }
    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }
    @PostMapping("/edit")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam Integer age) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setSurname(surname);
            user.setAge(age);
            userService.updateUser(user);
        }
        return "redirect:/users";
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.removeUser(id);
        return "redirect:/users";
    }
}
