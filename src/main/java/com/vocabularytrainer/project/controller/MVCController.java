package com.vocabularytrainer.project.controller;

import com.vocabularytrainer.project.db.VocabularyEntries;
import com.vocabularytrainer.project.db.VocabularyRepository; // Repository Interface

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MVCController {

    /* Setup access Database Repository Interface */
    VocabularyRepository vocabularyRepository;
    public MVCController(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    /* Welcome Page */
    @GetMapping("/")
    public String root() {
        return "index";
    }

    /* User Interface: Dashboard */
    @GetMapping("/user")
    public String userIndex(Model model) {

        // tell the thymeleaf which user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/index";
    }

    /* User StudyInterface in German*/
    @GetMapping("/user/studyInterfaceGerman")
    public String userStudyInterfaceGerman(Model model) {

        // tell the thymeleaf which user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/study_interface_german";
    }

    @GetMapping("/user/studyInterfaceEnglish")
    public String userStudyInterfaceEnglish(Model model) {

        // tell the thymeleaf which user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/study_interface_english";
    }


    @GetMapping("/user/studyInterface")
    public String userStudyInterface(Model model) {

        // tell the thymeleaf which user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        return "user/study_interface";
    }


    /* Show the All vocabularies in edit_vocab.html*/
    @GetMapping("user/editvoc")
    public String getUserEditVocabulary(Model model) {

        // tell the thymeleaf which user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/edit_vocab";
    }


    // user/editvoc/edit+${entry.id}}
    @GetMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_product");

        VocabularyEntries vocabularyEntries = this.vocabularyRepository.getEntryBasedOnId(id);


        System.out.println(vocabularyEntries.getId());


        //        Product product = service.get(id);
        //mav.addObject("product", product);



        return mav;
    }

    /* Show the Form and Let the user enter stuff */
    @GetMapping("/user/addvoc")
    // Model -> Thymeleaf model to access html "variables"
    public String getUserAddVocabulary(Model model) {


        VocabularyEntries vocabularyEntries = new VocabularyEntries();


        // Thymeleaf-variable for "get-form"
        model.addAttribute("addvoc", vocabularyEntries);

        return "user/addvoc_form";
    }



    /* Submit Data from Form using POST */
    @PostMapping("/user/addvoc")
    public String submitUserAddVocabulary(VocabularyEntries vocabularyEntries, Model model) {

        model.addAttribute("submitted", true);

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        vocabularyEntries.setUser(userDetails.getUsername());

        // save result in our Repository Interface
        VocabularyEntries result = this.vocabularyRepository.save(vocabularyEntries);

        // Thymeleaf-variable for "post-form" - add it
        model.addAttribute("addvoc", result);

        /// TODO: Fix redirecting
        return "user/addvoc_form";
    }

    /* Login Page */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /* Access Denied Page */
    @GetMapping("/request-denied")
    public String accessDenied() {
        return "access-denied-page";
    }
}