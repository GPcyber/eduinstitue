package com.example.eventplanner.controller;
import com.example.eventplanner.model.Course;
import com.example.eventplanner.model.User;
import com.example.eventplanner.model.appliedcourse;
import com.example.eventplanner.repository.Courserepository;
import com.example.eventplanner.repository.UserRepository;
import com.example.eventplanner.repository.appliedRepository;
import com.example.eventplanner.repository.enquiryRepository;
import com.example.eventplanner.service.Courseservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/////////////////////////////////////This controller deals with both user and admin functions
@Controller
public class usercontroller {

    @Autowired
    Courserepository courserepository;
    @Autowired
    appliedRepository appliedrepository;
    @Autowired
    enquiryRepository enquiryrepository;
    @Autowired
    Courseservice service;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/signup")
    public String signupLogin(@ModelAttribute User user,Model model) throws Exception
    {
        user.setUid(Long.parseLong("1"));
        userRepository.save(user);
        List entity=service.getActiveCourse();
        model.addAttribute("activecourse",entity);
        return "Login.html";
    }

    @GetMapping("/userpage")
    public String getuserhomepage()
    {
        return "userpage";
    }
    @GetMapping("/home/admin")
    public String getAdmin()
    {
        return "adminhome.html";
    }
    @GetMapping("home/admin/logout")
    public String afterLogout()
    {
        return "home.html";
    }


    @RequestMapping(path = {"/admin/activate/{uemail}"})
    public String activateasAdmin(Model model,@PathVariable ("uemail") Optional<String> email) throws Exception
    {
        if(email.isPresent()) {
            User enitity = service.getUserbyId(email.get());
            enitity.setUid(Long.parseLong("2"));
            userRepository.save(enitity);
        }
        return "redirect:/admin/userlist";
    }

    @RequestMapping(path = {"/admin/deactivate/{uemail}"})
    public String deactivateasAdmin(@PathVariable ("uemail") Optional<String> email) throws Exception
    {
        if(email.isPresent()) {
            User enitity = service.getUserbyId(email.get());
            enitity.setUid(Long.parseLong("1"));
            userRepository.save(enitity);
        }
        return "redirect:/admin/userlist";
    }

    @GetMapping("home/forgetpasswordhome")
    public String gethome()
    {
        return "forgetpasswordhome.html";
    }

    @RequestMapping (path={"/forgetpassword/"},method = RequestMethod.POST)
    public String getforgetpassword(@RequestParam("useremail")Optional<String> mail,Model model,@RequestParam("useremail") String mail1) throws Exception
    {
        if(userRepository.findById(mail1).isPresent())
        {User entity = service.getUserbyId(mail.get());
         model.addAttribute("user", entity);
         return "forgetpassword";
        }
        else
            return "405.html";
    }

    @PostMapping("/admin/signuppass")
    public String userLogin1(@ModelAttribute User user)
    {
        userRepository.save(user);
        return "redirect:/home/";
    }

    @GetMapping("/admin/userlist")
    public String getAdminUserList(Model model)
    {   List<User>entity =userRepository.findAll();
        model.addAttribute("user",entity);
        return "adminuserlist.html"; }

    @GetMapping("/admin/enquirydetails")
    public String getEnquiry (Model model){
        model.addAttribute( "enquiry",enquiryrepository.findAll());
        return "enquirydetails";
    }

    @GetMapping("/enquiry/delete/{eid}")
    public String deleteEnquiry(Model model, @PathVariable("eid") Long id){
        enquiryrepository.deleteById((id));
        return "redirect:/admin/enquirydetails";
    }

    @GetMapping("/appliedcourse")
    public String getAppliedCoursesListUser(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

       Optional <User> entity =userRepository.findById(currentPrincipalName);
       if (entity.isPresent())
       {
        User newuser=entity.get();
        Set<Course> c1= newuser.getCourses();
        model.addAttribute("userappliedcourse",c1);
       }
        return "userappliedcourse.html";
    }

    @GetMapping("/admin/appliedcourse")
    public String getAppliedCoursesListAdmin(Model model)
    {
        List<appliedcourse>entity=appliedrepository.findAll();
        model.addAttribute("userdetails",entity);
        return "adminappliedcourse.html";
    }

}
