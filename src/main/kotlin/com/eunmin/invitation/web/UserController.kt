package com.eunmin.invitation.web

import com.eunmin.invitation.service.UserService
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.boot.web.server.Cookie
import org.springframework.boot.web.servlet.server.Session
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
class UserController(
    private val userService: UserService
) {

    @GetMapping("/login")
    fun login(
        @RequestParam("code", required = false) code: String,
        redirectAttr: RedirectAttributes,
        response: HttpServletResponse
    ): String {
        val token = userService.login(code)
        val cookie = jakarta.servlet.http.Cookie("token", token)
        cookie.secure = true
        cookie.maxAge = 86400
        cookie.domain = "localhost"

        response.addCookie(cookie)
        redirectAttr.addFlashAttribute("auth", "login")
        return "redirect:/"
    }


    companion object {
        val LOGGER = KotlinLogging.logger {  }
    }
}