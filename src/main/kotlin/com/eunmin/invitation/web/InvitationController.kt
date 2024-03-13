package com.eunmin.invitation.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class InvitationController {

    @PostMapping("/my")
    fun newInvitation() {

    }

}