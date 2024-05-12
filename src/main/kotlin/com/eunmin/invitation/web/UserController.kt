package com.eunmin.invitation.web

import com.eunmin.invitation.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class UserController(
    private val userService: UserService,
    private val blackList : MutableList<String> = mutableListOf()
) {

    @GetMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) : String{
        val cookies = request.cookies
        cookies.forEach {
            it?.let {
                if(it.name == "token") {
                    it.maxAge = 0
                    response.addCookie(it)
                    blackList.add(it.value)
                }
            }
        }

        return "redirect:/"
    }

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