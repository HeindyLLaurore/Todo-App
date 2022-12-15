package com.thebeans.controllers;

import com.thebeans.models.AccountSettings;
import com.thebeans.models.Task;
import com.thebeans.models.User;
import com.thebeans.repositories.UserRepository;
import com.thebeans.security.CustomUserDetails;
import com.thebeans.services.SettingService;
import com.thebeans.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
public class SettingController {

    @Autowired
    SettingService settingService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String viewHomePage() {
        return "welcome";
    }

    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @RequestMapping(value = "/process_register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            settingService.save(user);

            return "welcome";

        }
    }

    @GetMapping("/user/accountSetting")
    public String accountSetting(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        /*
         *Spring Model used to pass data from the controller to the view page
         */
        String email = userDetails.getUsername();
        User user = settingService.getByEmail(email);
        model.addAttribute("user", AccountSettings.fromUser(user));

        return "account_setting";
    }

    @PostMapping("/user/accountSetting")
    public String saveAccountSetting(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     @Valid @ModelAttribute("user") AccountSettings user, BindingResult bindingResult,
                                     @RequestParam("image") MultipartFile image,
                                     Model model)
            throws IOException {

        boolean requireLogin = false;

        User dbUser = settingService.getByEmail(userDetails.getUsername());
        if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
            if (!user.getNewPassword().equals(user.getNewPasswordRepeat())) {
                bindingResult.addError(new FieldError("user" ,"newPasswordRepeat", "Passwords must match"));
            }
            if (!passwordEncoder.matches(user.getCurrentPassword(), userDetails.getPassword())) {
                bindingResult.addError(new FieldError("user" ,"currentPassword", "Current passwords is invalid"));
            }
            dbUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
            requireLogin = true;
        }
        if (!bindingResult.hasErrors()) {
            if (!dbUser.getEmail().equals(user.getEmail())) {
                requireLogin = true;
            }
            dbUser.setFirst_name(user.getFirstName());
            dbUser.setLast_name(user.getLastName());
            dbUser.setEmail(user.getEmail());
            dbUser.setMotto(user.getMotto());
            dbUser.setBirthday(user.getBirthday());
            dbUser.setPhone(user.getPhone());
            if(image.getBytes().length > 0) {
                dbUser.setPhoto(image.getBytes());
            }
            settingService.save(dbUser);
            if (requireLogin) {
                return "redirect:/login";
            }
            model.addAttribute("message", "Changes saved!");
        }
        return "account_setting";
    }

    @GetMapping("/user/photo")
    public @ResponseBody byte[] getImage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User user = settingService.getByEmail(userEmail);
        if (user == null) {
            return null;
        }
        return user.getPhoto();
    }

    /* ****** Planner Page ****** */
    @Autowired
    private TaskService service;

    @RequestMapping("/todo")
    public String toDoBoard(Model model, @ModelAttribute("task") Task task, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Task> listTasks = service.listAll();
        model.addAttribute("listTasks", listTasks);
        String userEmail = userDetails.getUsername();
        User user = settingService.getByEmail(userEmail);
        model.addAttribute("user", user);
        return "ToDo";
    }

    @RequestMapping("/addTask/{status}")
    public String addPage(@PathVariable(name = "status") String status, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Task task = new Task();
        task.setStatus(status);
        model.addAttribute("task", task);
        String userEmail = userDetails.getUsername();
        User user = settingService.getByEmail(userEmail);
        model.addAttribute("user", user);
        return "addTask";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Task> listTasks = service.listAll();
        model.addAttribute("listTasks", listTasks);
        String userEmail = userDetails.getUsername();
        User user = settingService.getByEmail(userEmail);
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            return "addTask";
        } else {
            task.setUser_id(user.user_id);
            service.save(task);
            return "redirect:/todo";
        }
    }

    @RequestMapping("/delete/{id}")
    public String deletePage(@PathVariable(name = "id") Long id, Model model) {
        service.delete(id);
        List<Task> listTasks = service.listAll();
        model.addAttribute("listTasks", listTasks);
        return "redirect:/todo";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("editTask");
        Task task = service.get(id);
        mav.addObject("task", task);
        service.delete(id);
        return mav;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public String saveStatus(@ModelAttribute("task") Task task, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Task> listTasks = service.listAll();
        model.addAttribute("listTasks", listTasks);
        System.out.println(task.getStatus());
        String userEmail = userDetails.getUsername();
        User user = settingService.getByEmail(userEmail);
        model.addAttribute("user", user);

        for (Task taskSelection : listTasks) {
            System.out.println(task.getTask_id());
            System.out.println(taskSelection.getTask_id());
            if (taskSelection.getTask_id() - task.getTask_id() == 0) {
                Task placeholder = new Task();
                placeholder.setTask_id(taskSelection.getTask_id());
                placeholder.setTitle(taskSelection.getTitle());
                placeholder.setDescription(taskSelection.getDescription());
                placeholder.setCategory(taskSelection.getCategory());
                placeholder.setDueDate(taskSelection.getDueDate());
                placeholder.setPriority(taskSelection.getPriority());
                placeholder.setStatus(task.getStatus());
                placeholder.setUser_id(user.user_id);
                service.delete(taskSelection.getTask_id());
                service.save(placeholder);
                System.out.println("Success");
            } else {
                System.out.println("Failure");
            }

        }
        return "redirect:/todo";
    }

}
