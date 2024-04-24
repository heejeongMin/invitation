package com.eunmin.invitation.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class InvitationController {

    @GetMapping("/")
    fun index(model: Model) : String {
//        model.addAttribute("token", )
        return "index"
    }
}